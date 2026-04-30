package com.shopverse.product_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.shopverse.product_service.enums.ProductCategory;

public class ProductResponse {
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Long stockQuantity;
	private ProductCategory productCategory;
	private boolean isActive;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public ProductResponse() {}
	
	public ProductResponse(Long id, String name, String description, BigDecimal price, Long stockQuantity, ProductCategory productCategory, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		this.setPrice(price);
		this.setStockQuantity(stockQuantity);
		this.setProductCategory(productCategory);
		this.setActive(isActive);
		this.setCreatedAt(createdAt);
		this.setUpdatedAt(updatedAt);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Long stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
