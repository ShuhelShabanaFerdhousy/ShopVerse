package com.shopverse.order_service.dto;

import com.shopverse.common.enums.PaymentMethod;

import jakarta.validation.constraints.NotNull;

public class OrderRequest {
	@NotNull
	private Long userId;
	
	@NotNull
	private PaymentMethod paymentMethod;
	
	public OrderRequest() {}
	
	public OrderRequest(Long userId, PaymentMethod paymentMethod) {
		this.userId = userId;
		this.paymentMethod = paymentMethod;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
