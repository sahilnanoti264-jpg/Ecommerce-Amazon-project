package com.amazon.exception;

public class CartIsEmptyException extends RuntimeException{
	public CartIsEmptyException(String msg){
		super(msg);
	}
}
