package com.amazon.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="cartItem")
@Data
@Table(name="amazon_cartItem")
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@SequenceGenerator(name = "cartItem_seq", initialValue = 1)
	private Long cartItemId;
	@ManyToOne
	@JoinColumn(name="productId")
	@JsonBackReference("product-cartItem")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="cartId")
	@JsonBackReference("cart-cartItem")
	private Cart cart;
	private Integer quantity;
}
