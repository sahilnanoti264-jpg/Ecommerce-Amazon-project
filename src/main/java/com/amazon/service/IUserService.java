package com.amazon.service;

import java.util.List;
import com.amazon.dto.ChangePasswordRequest;
import com.amazon.dto.UpdateUserRequest;
import com.amazon.dto.UserRequest;
import com.amazon.dto.UserResponse;
import com.amazon.enumeration.ProfileStatus;
import com.amazon.exception.UserNotFoundException;

import jakarta.mail.MessagingException;

public interface IUserService {
	public UserResponse registerUser(UserRequest request) throws IllegalArgumentException, MessagingException;
	public UserResponse getUserById(Long userId) throws IllegalArgumentException;
	public List<UserResponse> getAllUsers() throws IllegalArgumentException;
	public List<UserResponse> getAllAdmin() throws IllegalArgumentException;
	public UserResponse updateUser(Long userId,UpdateUserRequest request) throws UserNotFoundException;
	public String deleteUserById(Long userId)throws IllegalArgumentException, UserNotFoundException;
	public Boolean existByEmail(String email) throws IllegalArgumentException;
	public UserResponse createAdmin(UserRequest request) throws IllegalArgumentException;
	public String modifyStatus(Long userId, ProfileStatus status) throws UserNotFoundException;
	public String changePassword(ChangePasswordRequest request) throws UserNotFoundException;
}
