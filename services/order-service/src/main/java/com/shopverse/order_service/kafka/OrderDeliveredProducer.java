package com.shopverse.order_service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.shopverse.common.event.OrderDeliveredEvent;

@Component
public class OrderDeliveredProducer {
	@Value("${spring.kafka.topic.order-delivered}")
	private String TOPIC;
	
	private final KafkaTemplate<String, OrderDeliveredEvent> kafkaTemplate;
	
	public OrderDeliveredProducer(KafkaTemplate<String, OrderDeliveredEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendOrderDeliveredEvent(OrderDeliveredEvent event) {
		kafkaTemplate.send(TOPIC, event);
	}
}
