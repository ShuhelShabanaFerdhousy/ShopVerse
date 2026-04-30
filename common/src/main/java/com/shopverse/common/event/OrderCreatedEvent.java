package com.shopverse.common.event;

import java.math.BigDecimal;

import com.shopverse.common.enums.PaymentMethod;

public class OrderCreatedEvent {
	private Long orderId;
	private Long userId;
	private BigDecimal amount;
	private PaymentMethod paymentMethod;
	
	public OrderCreatedEvent() {}
	
	public OrderCreatedEvent(Long orderId, Long userId, BigDecimal amount, PaymentMethod paymentMethod) {
		this.orderId = orderId;
		this.userId = userId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
