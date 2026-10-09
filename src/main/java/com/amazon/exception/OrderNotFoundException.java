package com.amazon.exception;

public class OrderNotFoundException extends RuntimeException{
	
	public OrderNotFoundException(String msg) {
		super(msg);
	}
}
