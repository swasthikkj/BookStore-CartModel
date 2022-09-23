package com.bridgelabz.bookstorecartmodel.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponse {
	private int errorcode;
	private String message;
	private Object token;
	
	public CartResponse() {
		
	}
}
