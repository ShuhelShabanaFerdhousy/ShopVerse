package com.shopverse.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopverse.order_service.dto.ProductResponse;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {
	@GetMapping("/products/{id}")
	ProductResponse getProductById(@PathVariable Long id);
}
