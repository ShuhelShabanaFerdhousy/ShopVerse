package com.shopverse.product_service.exception;

public class InvalidStockOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidStockOperationException(String message) {
		super(message);
	}
}
