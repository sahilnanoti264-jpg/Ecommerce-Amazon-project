package com.amazon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.entity.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {

}
