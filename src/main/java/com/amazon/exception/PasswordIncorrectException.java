package com.amazon.exception;

public class PasswordIncorrectException extends RuntimeException{
	
	public PasswordIncorrectException(String msg) {
		super(msg);
	}
}
