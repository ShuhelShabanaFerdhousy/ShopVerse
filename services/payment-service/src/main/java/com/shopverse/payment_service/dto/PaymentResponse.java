package com.shopverse.payment_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.shopverse.common.enums.PaymentMethod;
import com.shopverse.common.enums.PaymentStatus;

public class PaymentResponse {
	private Long paymentId;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public PaymentResponse() {}
    
    public PaymentResponse(Long paymentId, Long orderId, Long userId, BigDecimal amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus, String transactionId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    	this.paymentId = paymentId;
    	this.orderId = orderId;
    	this.userId = userId;
    	this.amount = amount;
    	this.paymentMethod = paymentMethod;
    	this.paymentStatus = paymentStatus;
    	this.transactionId = transactionId;
    	this.createdAt = createdAt;
    	this.updatedAt = updatedAt;
    }
    
	public Long getPaymentId() {
		return paymentId;
	}
	
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
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

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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
