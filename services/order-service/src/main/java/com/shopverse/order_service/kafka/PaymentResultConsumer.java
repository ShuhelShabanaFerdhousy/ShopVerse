package com.shopverse.order_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.shopverse.common.event.PaymentResultEvent;
import com.shopverse.order_service.service.OrderService;

@Component
public class PaymentResultConsumer {
	private final OrderService orderService;
	
	public PaymentResultConsumer(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@KafkaListener(topics = "payment-result-events", groupId = "order-group")
	public void consumePaymentResultEvent(PaymentResultEvent event) {
		System.out.println("--- Payment Event Received ---");
	    System.out.println("OrderId: " + event.getOrderId());
	    System.out.println("PaymentStatus: " + event.getPaymentStatus());
	    System.out.println("PaymentMethod: " + event.getPaymentMethod());
	    
	    orderService.handlePaymentResult(event);
	}
}
