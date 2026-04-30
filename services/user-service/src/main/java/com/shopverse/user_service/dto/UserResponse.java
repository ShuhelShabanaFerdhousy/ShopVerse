package com.shopverse.user_service.dto;

import java.time.LocalDateTime;

import com.shopverse.user_service.enums.UserRole;
import com.shopverse.user_service.enums.UserStatus;

public class UserResponse {
	private Long id;
	private String username;
	private String email;
	private UserRole userRole;
	private UserStatus userStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public UserResponse() {}
	
	public UserResponse(Long id, String username, String email, UserRole userRole, UserStatus userStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.userRole = userRole;
		this.userStatus = userStatus;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
