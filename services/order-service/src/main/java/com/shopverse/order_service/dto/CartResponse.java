package com.shopverse.order_service.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
	private Long userId;
	private BigDecimal totalAmount;
	private List<CartItemResponse> items;
	
	public CartResponse() {}
	
	public CartResponse(Long userId, BigDecimal totalAmount, List<CartItemResponse> items) {
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.items = items;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<CartItemResponse> getItems() {
		return items;
	}

	public void setItems(List<CartItemResponse> items) {
		this.items = items;
	}
}
