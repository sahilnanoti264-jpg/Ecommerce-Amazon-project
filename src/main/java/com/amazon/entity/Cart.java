package com.amazon.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="cart")
@Data
@Table(name="amazon_cart")
@NoArgsConstructor
public class Cart{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@SequenceGenerator(name="cart_seq",initialValue = 1)
	private Long cartId;
	
	@OneToOne
	@JoinColumn(name="userId")
	@JsonBackReference("user-cart")
	private User user; // Added foreign key of user
	
	@OneToMany(mappedBy="cart",cascade=CascadeType.ALL, orphanRemoval = true) //orphoneremoval=true means remove database  
	@JsonManagedReference("cart-cartItem")
	private List<CartItem> cartItems=new ArrayList<>();
}
