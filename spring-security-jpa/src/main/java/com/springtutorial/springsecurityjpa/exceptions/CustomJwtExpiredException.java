package com.springtutorial.springsecurityjpa.exceptions;

public class CustomJwtExpiredException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomJwtExpiredException(String msg) {
		super(msg);
	}

}
