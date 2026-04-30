package com.shopverse.order_service.dto;

import com.shopverse.order_service.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateOrderStatusRequest {
	@NotNull
	private OrderStatus orderStatus;

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
