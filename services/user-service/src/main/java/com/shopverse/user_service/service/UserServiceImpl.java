package com.shopverse.user_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopverse.user_service.dto.UpdateEmailRequest;
import com.shopverse.user_service.dto.UpdatePasswordRequest;
import com.shopverse.user_service.dto.UpdateUsernameRequest;
import com.shopverse.user_service.dto.UserRequest;
import com.shopverse.user_service.dto.UserResponse;
import com.shopverse.user_service.entity.User;
import com.shopverse.user_service.enums.UserStatus;
import com.shopverse.user_service.exception.EmailAlreadyExistsException;
import com.shopverse.user_service.exception.InvalidUserStateException;
import com.shopverse.user_service.exception.PasswordPolicyViolationException;
import com.shopverse.user_service.exception.UserNotFoundException;
import com.shopverse.user_service.exception.UsernameAlreadyExistsException;
import com.shopverse.user_service.mapper.UserMapper;
import com.shopverse.user_service.repository.UserRepository;
import com.shopverse.user_service.utils.PasswordUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {
private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private User getUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
	
	@Override
	public UserResponse createUser(UserRequest request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new UsernameAlreadyExistsException("Username already exists.");
		}
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException("Email ID already exists.");
		}
		if(!PasswordUtils.isPasswordValid(request.getPassword())) {
			throw new PasswordPolicyViolationException("Password should be at least 8 characters long, should contain at least 1 lowercase, 1 uppercase, 1 digit and 1 special character. Password should not contain any whitespaces.");
		}
		
		User user = UserMapper.toEntity(request);
		user.setUserStatus(UserStatus.ACTIVE);
		
		User savedUser = userRepository.save(user);
		return UserMapper.toResponse(savedUser);
	}
	
	@Transactional(readOnly = true)
	@Override
	public UserResponse getUserById(Long id) {
		return UserMapper.toResponse(getUserEntity(id));
	}
	
	@Transactional(readOnly = true)
	@Override
	public UserResponse getUserByEmail(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
		return UserMapper.toResponse(user);
	}
	
	@Transactional(readOnly = true)
	@Override
	public UserResponse getUserByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
		return UserMapper.toResponse(user);
	}
	
	@Override
	public UserResponse updateUsername(Long id, UpdateUsernameRequest request) {
		User user = getUserEntity(id);
		
		if (user.getUserStatus() == UserStatus.DELETED) {
            throw new InvalidUserStateException("Cannot modify deleted user.");
        }
		
		String newUsername = request.getUsername();
		
		if(!newUsername.equals(user.getUsername()) && userRepository.existsByUsername(newUsername)) {
			throw new UsernameAlreadyExistsException("Username already exists.");
		}
		
		user.setUsername(newUsername);
		User updatedUser = userRepository.save(user);
		return UserMapper.toResponse(updatedUser);
	}
	
	@Override
	public UserResponse updateEmail(Long id, UpdateEmailRequest request) {
		User user = getUserEntity(id);
		
		if (user.getUserStatus() == UserStatus.DELETED) {
            throw new InvalidUserStateException("Cannot modify deleted user.");
        }
		
		String newEmail = request.getEmail();
		
		if(!newEmail.equals(user.getEmail()) && userRepository.existsByEmail(newEmail)) {
			throw new EmailAlreadyExistsException("Email already exists.");
		}
		
		user.setEmail(newEmail);
		User updatedUser = userRepository.save(user);
		return UserMapper.toResponse(updatedUser);
	}
	
	@Override
	public void updatePassword(Long id, UpdatePasswordRequest request) {
		User user = getUserEntity(id);
		String newPassword = request.getPassword();
		
		if (user.getUserStatus() == UserStatus.DELETED) {
		    throw new InvalidUserStateException("Cannot modify deleted user.");
		}
		if(!PasswordUtils.isPasswordValid(newPassword)) {
			throw new PasswordPolicyViolationException("Password should be at least 8 characters long, should contain at least 1 lowercase, 1 uppercase, 1 digit and 1 special character. Password should not contain any whitespaces.");
		}
		
		user.setPassword(newPassword);
		userRepository.save(user);
	}
	
	@Override
	public void deleteUser(Long id) {
		User user = getUserEntity(id);
		if (user.getUserStatus() == UserStatus.DELETED) {
            throw new InvalidUserStateException("User already deleted");
        }
		
		user.setUserStatus(UserStatus.DELETED);
		userRepository.save(user);
	}
	
	@Override
	public void blockUser(Long id) {
		User user = getUserEntity(id);
		
		if (user.getUserStatus() == UserStatus.DELETED) {
		    throw new InvalidUserStateException("Cannot modify deleted user.");
		}
		if (user.getUserStatus() == UserStatus.BLOCKED) {
            throw new InvalidUserStateException("User already blocked");
        }
		
		user.setUserStatus(UserStatus.BLOCKED);
		userRepository.save(user);
	}
	
	@Override
	public void deactivateUser(Long id) {
		User user = getUserEntity(id);
		
		if (user.getUserStatus() == UserStatus.DELETED) {
		    throw new InvalidUserStateException("Cannot modify deleted user.");
		}
		if (user.getUserStatus() == UserStatus.INACTIVE) {
            throw new InvalidUserStateException("User already inactive");
        }
		
		user.setUserStatus(UserStatus.INACTIVE);
		userRepository.save(user);
	}

	@Override
	public void activateUser(Long id) {
		User user = getUserEntity(id);
		
		if (user.getUserStatus() == UserStatus.DELETED) {
		    throw new InvalidUserStateException("Cannot modify deleted user.");
		}
		if (user.getUserStatus() == UserStatus.ACTIVE) {
            throw new InvalidUserStateException("User already active");
        }
		
		user.setUserStatus(UserStatus.ACTIVE);
		userRepository.save(user);
	}
}
