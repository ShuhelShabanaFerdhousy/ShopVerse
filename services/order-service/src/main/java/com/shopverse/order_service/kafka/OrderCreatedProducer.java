package com.shopverse.order_service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.shopverse.common.event.OrderCreatedEvent;

@Component
public class OrderCreatedProducer {
	@Value("${spring.kafka.topic.order-created}")
	private String TOPIC;
	private final  KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
	
	public OrderCreatedProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
	    this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendOrderCreatedEvent(OrderCreatedEvent event) {
		kafkaTemplate.send(TOPIC, event);
	}
}
