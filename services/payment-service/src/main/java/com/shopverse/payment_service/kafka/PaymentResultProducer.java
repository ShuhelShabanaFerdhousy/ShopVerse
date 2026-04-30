package com.shopverse.payment_service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.shopverse.common.event.PaymentResultEvent;

@Component
public class PaymentResultProducer {
	@Value("${spring.kafka.topic.payment-result}")
	private String TOPIC;
	
	private final KafkaTemplate<String, PaymentResultEvent> kafkaTemplate;
	
	public PaymentResultProducer(KafkaTemplate<String, PaymentResultEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendPaymentResultEvent(PaymentResultEvent event) {
		kafkaTemplate.send(TOPIC, event);
	}
}
