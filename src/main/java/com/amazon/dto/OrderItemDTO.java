package com.amazon.dto;

import com.amazon.entity.Order;
import com.amazon.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
	private Integer quantity;
	private Double price;
	private Double totalPrice;
	private Order order;
	private Product product;
}
