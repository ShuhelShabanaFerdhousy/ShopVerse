package com.shopverse.user_service.dto;

import com.shopverse.user_service.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRequest {
	@NotBlank
	@Size(min = 3, max = 50)
	private String username;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 6, max = 100)
	private String password;
	
	@NotNull
	private UserRole userRole;
	
	public UserRequest() {}
	
	public UserRequest(String username, String email, String password, UserRole userRole) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
}
