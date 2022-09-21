package com.bridgelabz.bookstorecartmodel.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CartNotFoundException extends RuntimeException {
	private int statuscode;
	private String message;

	public CartNotFoundException(int statuscode, String message) {
		super(message);
		this.statuscode = statuscode;
		this.message = message;
	}
}
