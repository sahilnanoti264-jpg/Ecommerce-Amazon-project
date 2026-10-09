package com.amazon.service;

import java.util.List;

import com.amazon.entity.Cart;
import com.amazon.entity.CartItem;
import com.amazon.exception.CartIsEmptyException;
import com.amazon.exception.CartItemNotFoundException;
import com.amazon.exception.CartNotFoundException;
import com.amazon.exception.ProductNotFoundException;
import com.amazon.exception.UserNotFoundException;

public interface ICartService {

    public Cart addToCart(Long userId, Long productId, Integer quantity) throws ProductNotFoundException, UserNotFoundException;

    public Cart getCartByUserId(Long userId) throws UserNotFoundException;

    public Cart modifyCartItemByQuantity(Long userId, Long cartItemId, Integer quantity)  throws UserNotFoundException, CartItemNotFoundException;

	public String removeCartItem(Long userId,Long cartItemId) throws CartItemNotFoundException, UserNotFoundException;

    public String clearCart(Long userId) throws UserNotFoundException;
}
