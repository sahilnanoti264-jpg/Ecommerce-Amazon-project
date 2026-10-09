package com.amazon.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.amazon.enumeration.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="order")
@Data
@Table(name="amazon_order")
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@SequenceGenerator(name="product_seq",initialValue = 1)
	private Long orderId;
	@ManyToOne
	@JoinColumn(name="userId")
	@JsonBackReference("user-order")
	@ToString.Exclude
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "paymentId")
	@JsonManagedReference("order-payment")
	private Payment payment;
	@OneToMany( mappedBy = "order",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true)
	@JsonManagedReference("order-orderItem")
	private List<OrderItem> orderItem;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	private BigDecimal totalAmount;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
}
