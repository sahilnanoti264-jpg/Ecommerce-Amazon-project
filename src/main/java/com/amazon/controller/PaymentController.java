package com.amazon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dto.PaymentRequest;
import com.amazon.dto.PaymentResponse;
import com.amazon.exception.OrderNotFoundException;
import com.amazon.service.IPaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {
	
	private final IPaymentService paymentService;
	
	public PaymentController(IPaymentService paymentService) {
		this.paymentService=paymentService;
	}
	
	@GetMapping("/status/{paymentId}")
	public String paymentStatus(@PathVariable Long paymentId) {
		 
		 return paymentService.paymentStatus(paymentId).toString();
	}
	
	@PostMapping("/create/{orderId}")
	public ResponseEntity<PaymentResponse> createPayment(@PathVariable Long orderId, @RequestBody PaymentRequest paymentRequest) 
			throws OrderNotFoundException {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.makePayment(orderId,paymentRequest));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<PaymentResponse>> getAllPayments(){
		return ResponseEntity.ok(paymentService.getAllPayments());
	}
	
	@GetMapping("/order/{orderId}")
	public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable Long orderId) {
		return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
	}
	
	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentResponse> getPaymentByPaymentId(@PathVariable Long paymentId){
		return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
	}
	
	@DeleteMapping("/delete/{paymentId}")
	public ResponseEntity<String> cancelPayment(@PathVariable Long paymentId){
		paymentService.cancelPayment(paymentId);
		return ResponseEntity.ok("Payment is cancelled.");
	}
	

}
