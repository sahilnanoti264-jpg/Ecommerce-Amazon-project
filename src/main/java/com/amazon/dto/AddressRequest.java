package com.amazon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
	
	@NotNull(message="plot no is required")
	private Integer plotNo;
	
	@NotBlank(message="street no is required")
	private String street;
	
	@NotBlank(message="city no is required")
	private String city;
	
	@NotBlank(message="state no is required")
	private String state;
	
	@NotNull(message="pincode no is required")
	private Long pincode;
	
	@NotBlank(message="country no is required")
	private String country;
}
