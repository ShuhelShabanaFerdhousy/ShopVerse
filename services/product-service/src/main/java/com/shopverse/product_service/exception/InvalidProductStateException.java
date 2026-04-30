package com.shopverse.product_service.exception;

public class InvalidProductStateException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidProductStateException(String message) {
		super(message);
	}
}
