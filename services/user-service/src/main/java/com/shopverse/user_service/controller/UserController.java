package com.shopverse.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shopverse.user_service.dto.UpdateEmailRequest;
import com.shopverse.user_service.dto.UpdatePasswordRequest;
import com.shopverse.user_service.dto.UpdateUsernameRequest;
import com.shopverse.user_service.dto.UserRequest;
import com.shopverse.user_service.dto.UserResponse;
import com.shopverse.user_service.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
		UserResponse response = userService.createUser(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		UserResponse response = userService.getUserById(id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<UserResponse> getUser(@RequestParam(required = false) String email, @RequestParam(required = false) String username) {
		if (email != null && username != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide either email or username, not both");
        }
		
		if(email != null) {
			UserResponse response = userService.getUserByEmail(email);
			return ResponseEntity.ok(response);
		}
		
		if(username != null) {
			UserResponse response = userService.getUserByUsername(username);
			return ResponseEntity.ok(response);
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Either email or username must be provided");
	}
	
	@PatchMapping("/{id}/username")
	public ResponseEntity<UserResponse> updateUsername(@PathVariable Long id, @Valid @RequestBody UpdateUsernameRequest newUsername) {
		UserResponse response = userService.updateUsername(id, newUsername);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}/email")
	public ResponseEntity<UserResponse> updateEmail(@PathVariable Long id, @Valid @RequestBody UpdateEmailRequest newEmail) {
		UserResponse response = userService.updateEmail(id, newEmail);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}/password")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UpdatePasswordRequest newPassword) {
		userService.updatePassword(id, newPassword);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}/delete")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
		userService.deactivateUser(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}/activate")
	public ResponseEntity<Void> activateUser(@PathVariable Long id) {
		userService.activateUser(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}/block")
	public ResponseEntity<Void> blockUser(@PathVariable Long id) {
		userService.blockUser(id);
		return ResponseEntity.noContent().build();
	}
}
