package com.amazon.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazon.dto.PaymentRequest;
import com.amazon.dto.PaymentResponse;
import com.amazon.email.EmailService;
import com.amazon.email.EmailSubject;
import com.amazon.email.template.PaymentEmailTemplate;
import com.amazon.entity.Order;
import com.amazon.entity.Payment;
import com.amazon.enumeration.OrderStatus;
import com.amazon.enumeration.PaymentStatus;
import com.amazon.exception.EmailSendingException;
import com.amazon.exception.OrderNotFoundException;
import com.amazon.exception.PaymentNotFoundException;
import com.amazon.repository.IOrderRepository;
import com.amazon.repository.IPaymentRepository;
import com.amazon.service.IPaymentService;

import jakarta.mail.MessagingException;

@Service("payment")
public class PaymentService implements IPaymentService {
	
	private final IPaymentRepository paymentRepository;
	private final IOrderRepository orderRepository;
	private final EmailService emailService;
	private final PaymentEmailTemplate template;
	
	public PaymentService(IPaymentRepository paymentRepository, IOrderRepository orderRepository,  EmailService emailService, PaymentEmailTemplate template) {
		this.paymentRepository=paymentRepository;
		this.orderRepository=orderRepository;
		this.emailService=emailService;	
		this.template=template;
	}
	
	@Override
	public PaymentStatus paymentStatus(Long paymentId) throws PaymentNotFoundException {
		if(paymentId==null)
			throw new IllegalArgumentException("Invalid payment details.");
		Payment payment=paymentRepository.findById(paymentId).orElseThrow(() -> 
					new PaymentNotFoundException("Payment not found"));
		return payment.getPaymentStatus();
	}
	
	@Override
	@Transactional
	public PaymentResponse makePayment(Long orderId, PaymentRequest paymentRequest) throws OrderNotFoundException {

	        if (paymentRequest == null) {
	            throw new IllegalArgumentException("Invalid payment details.");
	        }

	        Order order = orderRepository
	                .findById(orderId)
	                .orElseThrow(() ->
	                    new OrderNotFoundException("Order not found with ID: "+ orderId));

	        Payment payment = order.getPayment();

	        payment.setOrder(order);

	        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

	        payment.setAmount(order.getTotalAmount());

	        payment.setPaymentStatus(PaymentStatus.SUCCESS);
	        
	        order.setOrderStatus(OrderStatus.CONFIRMED);

	        payment.setPaymentDate(LocalDateTime.now());
	        
	        //Transaction will be set after payment done.
	        payment.setTransactionId(UUID.randomUUID().toString());
	        Payment savedPayment = paymentRepository.save(payment);
	        orderRepository.save(order);
	       //Email 
	        try {
				emailService.sentEmail(order.getUser().getEmail(), EmailSubject.PAYMENT_SUCCESS, template.build(savedPayment));

			}catch( MessagingException e) {
				throw new EmailSendingException("Unable to send registration email");
			}
	        
	        return toResponse(savedPayment);
	}
	
	@Override
	public PaymentResponse getPaymentById(Long paymentId) throws PaymentNotFoundException {
		if(paymentId==null)
			throw new IllegalArgumentException("Invalid paymentId");
		
		Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> 
			new PaymentNotFoundException("Payment not found"));
		
		 return toResponse(payment);
	}
	@Override
	public PaymentResponse getPaymentByOrderId(Long orderId) throws OrderNotFoundException {
		if(orderId==null)
			throw new IllegalArgumentException("Invalid orderId");
		
		Order order=orderRepository.findById(orderId).orElseThrow(() ->
		new OrderNotFoundException("Order not found"));
		
		Payment payment = order.getPayment();
		
		return toResponse(payment);
	}
	@Override
	public List<PaymentResponse> getAllPayments() {
		List<Payment> paymentList =paymentRepository.findAll();
		
		if(paymentList==null)
			throw new IllegalArgumentException("PaymentList is null");
		
		List<PaymentResponse> paymentResponseList=paymentList.stream().map(product -> toResponse(product)).toList();
				
		return paymentResponseList;
	}
	@Override
	public String cancelPayment(Long paymentId) throws PaymentNotFoundException {
		if(paymentId==null)
			throw new IllegalArgumentException("Invalid paymentId");
		
		Payment payment=paymentRepository.findById(paymentId).orElseThrow(() -> 
			new PaymentNotFoundException("Payment not found"));
		payment.setPaymentStatus(PaymentStatus.FAILED);
		
		paymentRepository.save(payment);
		return "Payment is cancelled.";
	}
	
	private String generateTransactionId() {
	    return "AMZ-" + java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
	}
	
	private PaymentResponse toResponse(Payment payment) {
		PaymentResponse response=new PaymentResponse();
		BeanUtils.copyProperties(payment, response);
		return response;
	}

}
