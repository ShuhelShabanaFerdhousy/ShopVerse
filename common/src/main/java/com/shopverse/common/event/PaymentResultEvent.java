package com.shopverse.common.event;

import com.shopverse.common.enums.PaymentMethod;
import com.shopverse.common.enums.PaymentStatus;

public class PaymentResultEvent {
	private Long orderId;
	private Long paymentId;
	private String transactionId;
	private PaymentStatus paymentStatus;
	private PaymentMethod paymentMethod;
	
	public PaymentResultEvent() {}
	
	public PaymentResultEvent(Long orderId, Long paymentId, String transactionId, PaymentStatus paymentStatus, PaymentMethod paymentMethod) {
		this.orderId = orderId;
		this.paymentId =paymentId;
		this.transactionId = transactionId;
		this.paymentStatus = paymentStatus;
		this.setPaymentMethod(paymentMethod);
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
