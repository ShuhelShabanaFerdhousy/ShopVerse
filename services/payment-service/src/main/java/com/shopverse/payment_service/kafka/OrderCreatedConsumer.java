package com.shopverse.payment_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.shopverse.common.event.OrderCreatedEvent;
import com.shopverse.payment_service.service.PaymentService;

@Component
public class OrderCreatedConsumer {
	private final PaymentService paymentService;
	
	public OrderCreatedConsumer(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@KafkaListener(topics = "order-created-events", groupId = "payment-group")
	public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
		System.out.println("--- OrderCreatedEvent Received ---");
        System.out.println("OrderId: " + event.getOrderId());
        System.out.println("UserId: " + event.getUserId());
        System.out.println("Amount: " + event.getAmount());
        System.out.println("PaymentMethod: " + event.getPaymentMethod());
		
		paymentService.handleOrderCreated(event);
	}
}
