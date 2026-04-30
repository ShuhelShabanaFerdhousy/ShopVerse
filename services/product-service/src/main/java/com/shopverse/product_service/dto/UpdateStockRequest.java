package com.shopverse.product_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class UpdateStockRequest {
	@NotNull
	@PositiveOrZero
	private Integer quantity;
	
	public UpdateStockRequest() {}
	
	public UpdateStockRequest(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
