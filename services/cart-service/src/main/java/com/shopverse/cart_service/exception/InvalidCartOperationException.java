package com.shopverse.cart_service.exception;

public class InvalidCartOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidCartOperationException(String message) {
		super(message);
	}
}
