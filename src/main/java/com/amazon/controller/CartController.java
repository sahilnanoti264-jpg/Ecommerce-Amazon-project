package com.amazon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dto.CartItemRequest;
import com.amazon.dto.UpdateCartRequest;
import com.amazon.entity.Cart;
import com.amazon.exception.ProductNotFoundException;
import com.amazon.exception.UserNotFoundException;
import com.amazon.service.ICartService;

@RestController
@RequestMapping("cart")
public class CartController {
	
	private final ICartService cartService;
	
	public CartController(ICartService cartService) {
		this.cartService=cartService;
	}
	
	@PostMapping("/add/{userId}")
	public ResponseEntity<String> addCart(@PathVariable Long userId, @RequestBody CartItemRequest request) 
			throws ProductNotFoundException, UserNotFoundException {
		cartService.addToCart(userId, request.getProduct().getProductId(), request.getQuantity());
		return ResponseEntity.status(HttpStatus.CREATED).body("Product is successfully added in the cart");
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Cart> retriveCartByUserId(@PathVariable Long userId){
		return ResponseEntity.ok(cartService.getCartByUserId(userId));
	}
	
	@PutMapping("update/{userId}")
	public ResponseEntity<String> updateCartItemByQuantity(@RequestBody UpdateCartRequest request){
		cartService.modifyCartItemByQuantity(request.getUserId(), request.getCartItemId(), request.getQuantity());
		return ResponseEntity.ok("Cart is successfully updated.");
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteCartItem(@PathVariable Long userId, @RequestParam Long cartItemId){
		cartService.removeCartItem(userId, cartItemId);
		return ResponseEntity.ok("CartItem is successfully deleted.");
	}  
	
	@DeleteMapping("/clear/{userId}")
	public ResponseEntity<String> clearCart(@PathVariable Long userId){
		cartService.clearCart(userId);
		return ResponseEntity.ok("Cart is successfully clear.");
	}
	
}
