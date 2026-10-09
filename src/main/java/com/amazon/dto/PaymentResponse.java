package com.amazon.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.amazon.enumeration.PaymentMethod;
import com.amazon.enumeration.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
	private Long paymentId;
	private BigDecimal amount;
	private PaymentStatus paymentStatus;
	private PaymentMethod paymentMethod;
	private String transactionId;
	private LocalDateTime paymentDate;
}
