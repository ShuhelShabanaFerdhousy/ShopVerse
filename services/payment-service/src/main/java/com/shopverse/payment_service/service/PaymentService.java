package com.shopverse.payment_service.service;

import com.shopverse.common.enums.PaymentStatus;
import com.shopverse.common.event.OrderCreatedEvent;
import com.shopverse.payment_service.dto.PaymentRequest;
import com.shopverse.payment_service.dto.PaymentResponse;

public interface PaymentService {
	PaymentResponse processPayment(PaymentRequest request);
    PaymentResponse getPaymentsByOrderId(Long orderId);
    PaymentResponse getPaymentById(Long paymentId);
    PaymentResponse updatePaymentStatus(Long paymentId, PaymentStatus status);
    void handleOrderCreated(OrderCreatedEvent event);
    void handleOrderDelivered(Long orderId);
}
