package com.amazon.dto;

import com.amazon.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
	
	private Long addressId;
	
	private User user;
	
	private Integer plotNo;
	
	private String street;
	
	private String city;
	
	private String state;
	
	private Long pincode;
	
	private String country;
}
