package com.shopverse.payment_service.exception;

import java.util.Map;

public class ValidationErrorResponse extends ErrorResponse {
	private Map<String, String> errors;
	
	public ValidationErrorResponse() {}
	
	public ValidationErrorResponse(Map<String, String> errors) {
		this.errors = errors;
	}

	public Map<String, String> getErrors() {
		return errors;
	}
	
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
