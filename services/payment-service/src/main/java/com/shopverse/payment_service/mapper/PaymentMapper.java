package com.shopverse.payment_service.mapper;

import com.shopverse.payment_service.dto.PaymentResponse;
import com.shopverse.payment_service.entity.Payment;

public class PaymentMapper {
	public static PaymentResponse toResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        
        response.setPaymentId(payment.getId());
        response.setOrderId(payment.getOrderId());
        response.setUserId(payment.getUserId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setTransactionId(payment.getTransactionId());
        response.setCreatedAt(payment.getCreatedAt());
        response.setUpdatedAt(payment.getUpdatedAt());
        
        return response;
    }
}
