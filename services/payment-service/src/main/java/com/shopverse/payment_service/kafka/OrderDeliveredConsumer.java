package com.shopverse.payment_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.shopverse.common.event.OrderDeliveredEvent;
import com.shopverse.payment_service.service.PaymentService;

@Component
public class OrderDeliveredConsumer {
	private final PaymentService paymentService;
	
	public OrderDeliveredConsumer(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@KafkaListener(topics = "order-delivered-events", groupId = "payment-group")
	public void consumeOrderDeliveredEvent(OrderDeliveredEvent event) {
		System.out.println("--- OrderDeliveredEvent Received ---");
        System.out.println("OrderId: " + event.getOrderId());
        
        paymentService.handleOrderDelivered(event.getOrderId());
	}
}
