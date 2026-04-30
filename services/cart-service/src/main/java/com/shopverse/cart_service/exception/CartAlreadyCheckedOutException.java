package com.shopverse.cart_service.exception;

public class CartAlreadyCheckedOutException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CartAlreadyCheckedOutException(String message) {
		super(message);
	}
}
