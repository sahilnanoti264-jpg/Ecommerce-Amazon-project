package com.amazon.dto;

import com.amazon.entity.Cart;
import com.amazon.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {
	private Product product;
	private Cart cart;
	private Integer quantity;
}
