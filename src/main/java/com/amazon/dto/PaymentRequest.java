package com.amazon.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amazon.entity.Order;
import com.amazon.enumeration.PaymentMethod;
import com.amazon.enumeration.PaymentStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
	public class PaymentRequest {
		 private BigDecimal amount;
		 private PaymentMethod paymentMethod;
	}
