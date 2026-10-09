package com.amazon.dto;

import java.util.ArrayList;
import java.util.List;

import com.amazon.entity.Address;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
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
	
	@NotBlank(message="Password is required")
	private String password;
	
	@NotEmpty(message="Address is required")     //@NotEmpty = Not Null + Not Blank  --> this valid for List
	@Valid            							//Ensure object inside object should be valid 
	private List<Address> address=new ArrayList<>();;;
}
