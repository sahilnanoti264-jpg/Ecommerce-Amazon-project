package com.amazon.dto;

import java.util.List;

import com.amazon.entity.CartItem;
import com.amazon.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
	private User user;
	private List<CartItem> cartItem;
}
