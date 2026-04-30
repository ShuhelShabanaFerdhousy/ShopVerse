package com.shopverse.user_service.service;

import com.shopverse.user_service.dto.UpdateEmailRequest;
import com.shopverse.user_service.dto.UpdatePasswordRequest;
import com.shopverse.user_service.dto.UpdateUsernameRequest;
import com.shopverse.user_service.dto.UserRequest;
import com.shopverse.user_service.dto.UserResponse;

public interface UserService {
	UserResponse createUser(UserRequest request);
	UserResponse getUserById(Long id);
	UserResponse getUserByEmail(String email);
	UserResponse getUserByUsername(String username);
	UserResponse updateUsername(Long id, UpdateUsernameRequest request);
	UserResponse updateEmail(Long id, UpdateEmailRequest request);
	void updatePassword(Long id, UpdatePasswordRequest request);
	void deleteUser(Long id);
	void blockUser(Long id);
	void deactivateUser(Long id);
	void activateUser(Long id);
}
