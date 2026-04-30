package com.shopverse.user_service.exception;

public class InvalidUserStateException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidUserStateException(String message) {
		super(message);
	}
}
