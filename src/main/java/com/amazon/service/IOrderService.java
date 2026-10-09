package com.amazon.service;

import java.util.List;

import com.amazon.dto.OrderRequest;
import com.amazon.entity.Order;
import com.amazon.enumeration.OrderStatus;
import com.amazon.exception.OrderNotFoundException;
import com.amazon.exception.UserNotFoundException;

public interface IOrderService {
	 public String placeOrder(OrderRequest orderRequest)throws UserNotFoundException;

	 public Order getOrderById(Long orderId) throws OrderNotFoundException;

	 public List<Order> getOrdersByUser(Long userId) throws UserNotFoundException;

	 public String cancelOrder(Long orderId) throws OrderNotFoundException;

	 public void updateOrderStatus(Long orderId, OrderStatus status) throws OrderNotFoundException;
	 
	 public List<Order> getAllOrders();
	 
	 public List<Order> orderHistory(Long userId) throws UserNotFoundException;
	 
	 public OrderStatus orderStatus(Long orderId) throws OrderNotFoundException;
	 
}