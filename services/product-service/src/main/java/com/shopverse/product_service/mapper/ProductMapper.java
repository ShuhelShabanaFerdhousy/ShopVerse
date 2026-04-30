package com.shopverse.product_service.mapper;

import com.shopverse.product_service.dto.ProductRequest;
import com.shopverse.product_service.dto.ProductResponse;
import com.shopverse.product_service.entity.Product;

public final class ProductMapper {
	public static Product toEntity(ProductRequest request) {
		Product product = new Product();
		
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStockQuantity(request.getStockQuantity());
		product.setProductCategory(request.getProductCategory());
		product.setActive(true);
		
		return product;
	}
	
	public static ProductResponse toResponse(Product product) {
		ProductResponse response = new ProductResponse();
		
		response.setId(product.getId());
		response.setName(product.getName());
		response.setDescription(product.getDescription());
		response.setPrice(product.getPrice());
		response.setStockQuantity(product.getStockQuantity());
		response.setProductCategory(product.getProductCategory());
		response.setActive(product.isActive());
		response.setCreatedAt(product.getCreatedAt());
		response.setUpdatedAt(product.getUpdatedAt());
		
		return response;
	}
}
