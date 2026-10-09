package com.amazon.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity(name="product")
@Data
@Table(name="amazon_product")
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@SequenceGenerator(name="product_seq",initialValue = 1)
	private Long productId;
	
	private String productName;
	
	private String category;

	private Double price;
	
	private String description;
	
	@OneToMany(mappedBy="product", cascade=CascadeType.ALL)
	@JsonManagedReference("product-cartItem")
	private List<CartItem> cartItems=new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	@JsonManagedReference("product-orderItem")
	private List<OrderItem> orderItem=new ArrayList<>();
	
}
