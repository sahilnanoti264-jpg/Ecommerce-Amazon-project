package com.amazon.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="amazon_orderItem")
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long orderItemId;
	private Integer quantity;
	private Double price;

	@ManyToOne
	@JoinColumn(name = "orderId")
	@JsonBackReference("order-orderItem")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "productId")
	@JsonBackReference("product-orderItem")
	private Product product;
}
