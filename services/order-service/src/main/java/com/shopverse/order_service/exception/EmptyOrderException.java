package com.shopverse.order_service.exception;

public class EmptyOrderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmptyOrderException(String message) {
		super(message);
	}
}
