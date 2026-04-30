package com.shopverse.product_service.dto;

import java.math.BigDecimal;

import com.shopverse.product_service.enums.ProductCategory;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ProductRequest {
	@NotBlank
	@Size(max = 150)
	private String name;
	
	@NotBlank
	@Size(max = 500)
	private String description;
	
	@NotNull
	@DecimalMin(value = "0.01", inclusive = true)
	@Digits(integer = 10, fraction = 2)
	private BigDecimal price;
	
	@NotNull
	@PositiveOrZero
	private Long stockQuantity;
	
	@NotNull
	private ProductCategory productCategory;
	
	public ProductRequest() {}
	
	public ProductRequest(String name, String description, BigDecimal price, Long stockQuantity, ProductCategory productCategory) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.productCategory = productCategory;
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
}
