package com.bridgelabz.bookstorecartmodel.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.bookstorecartmodel.exception.CartNotFoundException;
import com.bridgelabz.bookstorecartmodel.util.CartResponse;
@ControllerAdvice
public class CartExceptionHandler {
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<CartResponse> handleId(CartNotFoundException ab) {
		CartResponse response = new CartResponse();
		response.setErrorcode(400);
		response.setMessage(ab.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
