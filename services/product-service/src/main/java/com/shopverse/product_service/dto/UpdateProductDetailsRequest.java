package com.shopverse.product_service.dto;

import com.shopverse.product_service.enums.ProductCategory;

public class UpdateProductDetailsRequest {
	private String name;
	private String description;
	private ProductCategory productCategory;
	
	public UpdateProductDetailsRequest() {}

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

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
}
