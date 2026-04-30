package com.shopverse.order_service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.shopverse.common.event.StockReductionEvent;

@Component
public class StockReductionProducer {
	@Value("${spring.kafka.topic.stock-reduction}")
	private String TOPIC;
	private final  KafkaTemplate<String, StockReductionEvent> kafkaTemplate;
	
	public StockReductionProducer(KafkaTemplate<String, StockReductionEvent> kafkaTemplate) {
	    this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendStockReductionEvent(StockReductionEvent event) {
		kafkaTemplate.send(TOPIC, event);
	}
}
