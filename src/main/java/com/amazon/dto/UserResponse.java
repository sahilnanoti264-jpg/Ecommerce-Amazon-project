package com.amazon.dto;

import java.util.List;

import com.amazon.entity.Address;
import com.amazon.enumeration.ProfileStatus;
import com.amazon.enumeration.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private Long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private Role role;
	private ProfileStatus status;
	private List<Address> address;
}
