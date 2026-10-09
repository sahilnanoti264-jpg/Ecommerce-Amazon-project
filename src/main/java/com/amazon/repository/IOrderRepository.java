package com.amazon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.entity.Order;
import com.amazon.entity.User;

public interface IOrderRepository extends JpaRepository<Order, Long> {
	public List<Order> findByUser(User user);
}
