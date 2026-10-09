package com.amazon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.entity.Cart;

public interface ICartRepository extends JpaRepository<Cart, Long> {
	
}	
