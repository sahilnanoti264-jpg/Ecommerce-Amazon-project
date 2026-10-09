package com.amazon.implementation;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazon.dto.CartItemRequest;
import com.amazon.entity.Cart;
import com.amazon.entity.CartItem;
import com.amazon.entity.Product;
import com.amazon.entity.User;
import com.amazon.exception.CartItemNotFoundException;
import com.amazon.exception.ProductNotFoundException;
import com.amazon.exception.UserNotFoundException;
import com.amazon.repository.ICartItemRepository;
import com.amazon.repository.ICartRepository;
import com.amazon.repository.IProductRepository;
import com.amazon.repository.IUserRepository;
import com.amazon.service.ICartService;

@Service("cart")
public class CartService implements ICartService {
	
	private final ICartRepository cartRepository;
	private final IProductRepository productRepository;
	private final IUserRepository userRepository;
	private final ICartItemRepository cartItemRepository;
	
	public CartService(ICartRepository cartRepository,IProductRepository productRepository,
			IUserRepository userRepository,ICartItemRepository cartItemRepository) {
		this.cartRepository=cartRepository;
		this.productRepository=productRepository;
		this.userRepository=userRepository;
		this.cartItemRepository=cartItemRepository;
	}

	@Override
	public Cart addToCart(Long userId, Long productId, Integer quantity) throws ProductNotFoundException, UserNotFoundException {
		
		if(userId==null || productId==null || quantity==null)
			throw new IllegalArgumentException("Invalid cart details");
		
		Product product =productRepository.findById(productId).orElseThrow(() -> 
				new ProductNotFoundException("Product not found"));
		User user =userRepository.findById(userId).orElseThrow(() ->
					new UserNotFoundException("User not found"));
		
		Cart cart =user.getCart();  // user cart fetch
		
		CartItem existingItem = cart.getCartItems().stream()
				.filter(prod -> prod.getProduct().getProductId().equals(productId)).findFirst().orElse(null);
		
		if(existingItem!=null) {
			existingItem.setQuantity(existingItem.getQuantity()+quantity);
		}
		else {
			CartItem cartItem =new CartItem();
			CartItemRequest cartItemRequest=new CartItemRequest(product,cart,quantity);
			BeanUtils.copyProperties(cartItemRequest, cartItem);
			cart.getCartItems().add(cartItem);
		}
		 
		return cartRepository.save(cart);
	}

	@Override
	public Cart getCartByUserId(Long userId) throws UserNotFoundException {
		if(userId==null) throw new IllegalArgumentException("Invalid userId");
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		return user.getCart();
	}

	@Override
	public Cart modifyCartItemByQuantity(Long userId, Long cartItemId, Integer quantity) throws UserNotFoundException, CartItemNotFoundException {
		if(userId==null || cartItemId==null || quantity==null)
			throw new IllegalArgumentException("Invalid cart details");
		
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
		
		Cart userCart=user.getCart();
		
		CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow(() -> 
				new CartItemNotFoundException("CartItem not found"));
		
		cartItem.setQuantity(cartItem.getQuantity()+quantity);
		
		return cartRepository.save(userCart);
	}

	@Override
	public String removeCartItem(Long userId,Long cartItemId) throws CartItemNotFoundException, UserNotFoundException {
		if(cartItemId==null || userId==null) 
			throw new IllegalArgumentException("Invalid cartItem details ");
		
		User user = userRepository.findById(userId).orElseThrow(() ->
			new UserNotFoundException("User not found"));
		
		Cart cart=user.getCart();
		
		CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow(() -> 
			new CartItemNotFoundException("CartItem not found"));
		
		if(!cart.getCartItems().contains(cartItem))
			throw new CartItemNotFoundException("CartItem not found");
		
		cartItemRepository.deleteById(cartItemId);
		
		return "CartItem is successfully deleted.";
	}

	@Transactional
	@Override
	public String clearCart(Long userId) throws UserNotFoundException {
		if(userId==null) 
			throw new IllegalArgumentException("Invalid userId.");
		
		User user = userRepository.findById(userId).orElseThrow(() ->
			new UserNotFoundException("User not found"));
		
		Cart cart=user.getCart();
		
		cart.getCartItems().clear();
		
		return "Cart is successfully cleared.";
	}

	 
	
}