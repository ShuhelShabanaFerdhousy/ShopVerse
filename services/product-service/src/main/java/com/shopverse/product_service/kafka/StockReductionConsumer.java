package com.shopverse.product_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.shopverse.common.event.StockReductionEvent;
import com.shopverse.product_service.service.ProductService;

@Component
public class StockReductionConsumer {
	private final ProductService productService;
	
	public StockReductionConsumer(ProductService productService) {
		this.productService = productService;
	}
	
	@KafkaListener(topics = "stock-reduction-events", groupId = "product-group")
	public void consumeStockReductionEvent(StockReductionEvent event) {
		System.out.println("--- StockReductionEvent Received ---");
        System.out.println("OrderId: " + event.getOrderId());
        
        productService.handleStockReduction(event);
	}
}
