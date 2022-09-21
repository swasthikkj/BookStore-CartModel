package com.bridgelabz.bookstorecartmodel.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.bookstorecartmodel.exception.CartNotFoundException;
import com.bridgelabz.bookstorecartmodel.util.Response;
@ControllerAdvice
public class CartExceptionHandler {
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<Response> handleId(CartNotFoundException ab) {
		Response response = new Response();
		response.setErrorcode(400);
		response.setMessage(ab.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
