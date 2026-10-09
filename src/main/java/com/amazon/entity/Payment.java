	package com.amazon.entity;
	
	import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.amazon.enumeration.PaymentMethod;
import com.amazon.enumeration.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
	
	@Entity(name="payment")
	@Table(name="amazon_payment")
	@Data
	@NoArgsConstructor
	public class Payment {
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@SequenceGenerator(name="payment_seq",initialValue = 1)
		private Long paymentId;
		
		private BigDecimal amount;
		
		@Enumerated(EnumType.STRING)
		private PaymentStatus paymentStatus;
		
		@Enumerated(EnumType.STRING)
		private PaymentMethod paymentMethod;
		 
		private String transactionId;
		
		@OneToOne(mappedBy = "payment")
		@JsonBackReference("order-payment")
		private Order order;
		
		@CreationTimestamp
		private LocalDateTime paymentDate;
	}
