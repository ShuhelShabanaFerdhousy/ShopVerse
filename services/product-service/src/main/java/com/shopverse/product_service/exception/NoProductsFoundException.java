package com.shopverse.product_service.exception;

public class NoProductsFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NoProductsFoundException(String message) {
		super(message);
	}
}
