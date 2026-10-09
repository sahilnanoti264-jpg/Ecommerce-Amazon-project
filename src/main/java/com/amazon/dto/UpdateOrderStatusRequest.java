package com.amazon.dto;

import com.amazon.enumeration.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusRequest {
	private Long orderId;
	private OrderStatus status;
}
