package com.amazon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartRequest {
	private Long userId;
	private Long cartItemId;
	private Integer quantity;
	
}
