package com.amazon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
	@NotBlank(message="Username is required")
	private String userName;
	
	@NotBlank(message="FirstName is required")
	private String firstName;
	
	@NotBlank(message="LastName is required")
	private String lastName;
	
	@NotBlank(message="PhoneNumber is required")
	private String phoneNumber;
	
	@NotBlank(message="Email is required")
	@Email(message="Invalid email.")
	private String email;
}
