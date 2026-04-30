package com.shopverse.payment_service.exception;

public class OrderServiceUnavailableException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OrderServiceUnavailableException(String message) {
		super(message);
	}
}
