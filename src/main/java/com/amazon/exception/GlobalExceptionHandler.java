package com.amazon.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amazon.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e, HttpServletRequest request) {
		ErrorResponse error = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not Found",
				e.getMessage(), request.getRequestURI());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAddressNotFoundException(AddressNotFoundException e, HttpServletRequest request){
		ErrorResponse error=new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),"Not Found",e.getMessage(),request.getRequestURI());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CartIsEmptyException.class)
	public ResponseEntity<ErrorResponse> handleCartIsEmptyException(CartIsEmptyException e, HttpServletRequest request){
		ErrorResponse error=new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),"Not Found",e.getMessage(),request.getRequestURI());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCartNotFoundException(CartNotFoundException e, HttpServletRequest request){
		ErrorResponse error=new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),"Not Found",e.getMessage(),request.getRequestURI());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CartItemNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCartItemNotFoundException(CartItemNotFoundException e, HttpServletRequest request){
		ErrorResponse error=new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),"Not Found",e.getMessage(),request.getRequestURI());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException e, HttpServletRequest request){
		ErrorResponse error=new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),"Not Found",e.getMessage(),request.getRequestURI());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PaymentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlePaymentNotFoundException(PaymentNotFoundException e, HttpServletRequest request){
		ErrorResponse error=new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),"Not Found",e.getMessage(),request.getRequestURI());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e, HttpServletRequest request){
		ErrorResponse error=new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),"Not Found",e.getMessage(),request.getRequestURI());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(EmailSendingException.class)
	public ResponseEntity<ErrorResponse> handleEmailSendingException(EmailSendingException e, HttpServletRequest request){
		ErrorResponse error=new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),"Not Found",e.getMessage(),request.getRequestURI());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
}
