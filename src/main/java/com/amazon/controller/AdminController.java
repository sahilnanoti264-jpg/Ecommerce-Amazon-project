package com.amazon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dto.UpdateUserRequest;
import com.amazon.dto.UserRequest;
import com.amazon.dto.UserResponse;
import com.amazon.service.IUserService;

@RestController
@RequestMapping("admin")
public class AdminController {
private final IUserService userService;
	
	public AdminController(IUserService userService) {
		this.userService=userService;	
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getAllUser(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/admins")
	public ResponseEntity<List<UserResponse>> getAllAdmin(){
		return ResponseEntity.ok(userService.getAllAdmin());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
		return ResponseEntity.ok(userService.getUserById(userId));
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRequest request) {
		userService.createAdmin(request);
		return ResponseEntity.status(HttpStatus.CREATED).body("User is registered successfully.");
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
		userService.updateUser(userId, request);
		return ResponseEntity.ok("User is updated successfully.");
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId){
		userService.deleteUserById(userId);
		return ResponseEntity.ok("User is deleted successfully.");
	}
}
