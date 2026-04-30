package com.shopverse.user_service.exception;

public class PasswordPolicyViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PasswordPolicyViolationException(String message) {
		super(message);
	}
}
