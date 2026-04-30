package com.shopverse.order_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class UpdateStockRequest {
	@NotNull
	@PositiveOrZero
	private Long quantity;
	
	public UpdateStockRequest() {}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
