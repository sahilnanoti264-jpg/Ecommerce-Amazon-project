package com.amazon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dto.OrderRequest;
import com.amazon.dto.UpdateOrderStatusRequest;
import com.amazon.entity.Order;
import com.amazon.exception.UserNotFoundException;
import com.amazon.service.IOrderService;

@RestController
@RequestMapping("order")
public class OrderController {
	private final IOrderService orderService;
	
	public OrderController(IOrderService orderService) {
		this.orderService=orderService;
	}
	
	@PostMapping("/place")
	public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) throws UserNotFoundException{
		orderService.placeOrder(orderRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body("Order is successfully placed.");
	}
	
	@GetMapping("/orders/{userId}")
	public ResponseEntity<List<Order>> getAllOrderByUserId(@PathVariable Long userId) {
		return ResponseEntity.ok(orderService.getOrdersByUser(userId));
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderByOrderId(@PathVariable Long orderId){
		return ResponseEntity.ok(orderService.getOrderById(orderId));
	}
	
	@PatchMapping("/cancel/{orderId}")
	public ResponseEntity<String> cancelOrder(@PathVariable Long orderId){
		return ResponseEntity.ok(orderService.cancelOrder(orderId));
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateOrderStatus(@RequestBody UpdateOrderStatusRequest request){
		orderService.updateOrderStatus(request.getOrderId(), request.getStatus());
		return ResponseEntity.ok("Order is successfully updated.");
	}
	
	@GetMapping("/history/{userId}")
	public ResponseEntity<List<Order>> orderHistory(@PathVariable Long userId){
		return ResponseEntity.ok(orderService.orderHistory(userId));
	}
	
	@GetMapping("/getAllOrder")
	public ResponseEntity<List<Order>> getAllOrders(){
		return ResponseEntity.ok(orderService.getAllOrders());
	}
}
