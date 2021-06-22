package com.springtutorial.springsecurityjpa.exceptions;

public class CustomJwtInvalidException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public CustomJwtInvalidException(String msg) {
		super(msg);
	}

}
