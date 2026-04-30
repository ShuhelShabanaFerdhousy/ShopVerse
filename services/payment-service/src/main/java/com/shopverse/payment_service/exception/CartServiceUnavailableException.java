package com.shopverse.payment_service.exception;

public class CartServiceUnavailableException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CartServiceUnavailableException(String message) {
		super(message);
	}
}
