package com.shopverse.payment_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopverse.common.enums.PaymentMethod;
import com.shopverse.common.enums.PaymentStatus;
import com.shopverse.common.event.OrderCreatedEvent;
import com.shopverse.common.event.PaymentResultEvent;
import com.shopverse.payment_service.client.CartClient;
import com.shopverse.payment_service.dto.PaymentRequest;
import com.shopverse.payment_service.dto.PaymentResponse;
import com.shopverse.payment_service.entity.Payment;
import com.shopverse.payment_service.exception.PaymentNotFoundException;
import com.shopverse.payment_service.exception.PaymentProcessingException;
import com.shopverse.payment_service.kafka.PaymentResultProducer;
import com.shopverse.payment_service.mapper.PaymentMapper;
import com.shopverse.payment_service.repository.PaymentRepository;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final CartClient cartClient;
    private final PaymentResultProducer paymentResultProducer;

    public PaymentServiceImpl(PaymentRepository paymentRepository, CartClient cartClient, PaymentResultProducer paymentResultProducer) {
        this.paymentRepository = paymentRepository;
        this.cartClient = cartClient;
        this.paymentResultProducer = paymentResultProducer;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        paymentRepository.findByOrderIdAndPaymentStatus(request.getOrderId(), PaymentStatus.SUCCESS).ifPresent(payment -> { throw new PaymentProcessingException("Payment already completed for order: " + request.getOrderId());});
        Payment payment = paymentRepository.findByOrderIdAndPaymentStatus(request.getOrderId(), PaymentStatus.PENDING).orElseThrow(() -> new PaymentNotFoundException("Payment not initiated for order: " + request.getOrderId()));

        try {
            if (payment.getPaymentMethod() != PaymentMethod.COD) {
                payment.setPaymentStatus(PaymentStatus.SUCCESS);
            }

            Payment savedPayment = paymentRepository.save(payment);
            
            PaymentResultEvent event = new PaymentResultEvent(savedPayment.getOrderId(), savedPayment.getId(), savedPayment.getTransactionId(), savedPayment.getPaymentStatus(), savedPayment.getPaymentMethod());
            paymentResultProducer.sendPaymentResultEvent(event);

            if (savedPayment.getPaymentStatus() == PaymentStatus.SUCCESS && savedPayment.getPaymentMethod() != PaymentMethod.COD) {
                cartClient.clearCart(payment.getUserId());
            }
            
            return PaymentMapper.toResponse(savedPayment);
        } catch (Exception ex) {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            
            PaymentResultEvent event = new PaymentResultEvent(payment.getOrderId(), payment.getId(), payment.getTransactionId(), payment.getPaymentStatus(), payment.getPaymentMethod());
            paymentResultProducer.sendPaymentResultEvent(event);
            throw new PaymentProcessingException("Payment failed due to processing error");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public PaymentResponse getPaymentsByOrderId(Long orderId) {
    	Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new PaymentNotFoundException("No payment found for order: " + orderId));
        return PaymentMapper.toResponse(payment);
    }

    @Transactional(readOnly = true)
    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
    	Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + paymentId));
    	return PaymentMapper.toResponse(payment);
    }
    
    @Override
    public PaymentResponse updatePaymentStatus(Long paymentId, PaymentStatus status) {
    	Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + paymentId));

        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            throw new PaymentProcessingException("Payment already completed");
        }

        payment.setPaymentStatus(status);
        Payment savedPayment = paymentRepository.save(payment);
        
        PaymentResultEvent event = new PaymentResultEvent(savedPayment.getOrderId(), savedPayment.getId(), savedPayment.getTransactionId(), savedPayment.getPaymentStatus(), savedPayment.getPaymentMethod());
        paymentResultProducer.sendPaymentResultEvent(event);
        return PaymentMapper.toResponse(savedPayment);
    }
    
    @Override
    public void handleOrderCreated(OrderCreatedEvent event) {
    	if (paymentRepository.findByOrderId(event.getOrderId()).isPresent()) {
		    return;
		}
		
		Payment payment = new Payment();
		
		payment.setOrderId(event.getOrderId());
		payment.setUserId(event.getUserId());
		payment.setAmount(event.getAmount());
		payment.setPaymentMethod(event.getPaymentMethod());
		payment.setPaymentStatus(PaymentStatus.PENDING);
		payment.setTransactionId(UUID.randomUUID().toString());
		
		paymentRepository.save(payment);
    }
    
    @Override
    public void handleOrderDelivered(Long orderId) {
    	Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new PaymentNotFoundException("No payment found for order id: " + orderId));
		
		if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
		    return;
		}
		
		payment.setPaymentStatus(PaymentStatus.SUCCESS);
		paymentRepository.save(payment);
    }
}
