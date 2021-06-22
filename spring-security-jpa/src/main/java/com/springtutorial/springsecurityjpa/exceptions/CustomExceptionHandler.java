package com.springtutorial.springsecurityjpa.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> handleJwtExpireException(IOException e){
		//return ResponseEntity.ok(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ProductExecption.class)
	public ResponseEntity<?> handleProductException(ProductExecption e){
		//return ResponseEntity.ok(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
	}

}
