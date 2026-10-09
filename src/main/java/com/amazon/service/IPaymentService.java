package com.amazon.service;

import java.util.List;

import com.amazon.dto.PaymentRequest;
import com.amazon.dto.PaymentResponse;
import com.amazon.enumeration.PaymentStatus;
import com.amazon.exception.OrderNotFoundException;
import com.amazon.exception.PaymentNotFoundException;

public interface IPaymentService {
	public PaymentResponse makePayment(Long orderId, PaymentRequest paymentRequest) throws OrderNotFoundException ;

    public PaymentResponse getPaymentById(Long paymentId) throws PaymentNotFoundException;

    public PaymentResponse getPaymentByOrderId(Long orderId) throws OrderNotFoundException ;

    public List<PaymentResponse> getAllPayments();

    public String cancelPayment(Long paymentId)  throws PaymentNotFoundException ;
    
	public PaymentStatus paymentStatus(Long paymentId) throws PaymentNotFoundException ;
}
