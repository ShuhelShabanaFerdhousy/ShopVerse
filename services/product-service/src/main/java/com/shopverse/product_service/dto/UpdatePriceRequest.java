package com.shopverse.product_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public class UpdatePriceRequest {
	@NotNull
	@DecimalMin(value = "0.01", inclusive = true)
	@Digits(integer = 10, fraction = 2)
    private BigDecimal price;
	
	public UpdatePriceRequest() {}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
