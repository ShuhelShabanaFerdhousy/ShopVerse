package com.shopverse.user_service.mapper;

import com.shopverse.user_service.dto.UserRequest;
import com.shopverse.user_service.dto.UserResponse;
import com.shopverse.user_service.entity.User;

public final class UserMapper {
	private UserMapper() {}
	
	public static User toEntity(UserRequest request) {
		User user = new User();
		
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setUserRole(request.getUserRole());
		
		return user;
	}
	
	public static UserResponse toResponse(User user) {
		UserResponse response = new UserResponse();
		
		response.setId(user.getId());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setUserRole(user.getUserRole());
		response.setUserStatus(user.getUserStatus());
		response.setCreatedAt(user.getCreatedAt());
		response.setUpdatedAt(user.getUpdatedAt());
		
		return response;
	}
}
