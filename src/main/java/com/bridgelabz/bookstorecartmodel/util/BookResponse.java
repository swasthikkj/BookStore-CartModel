package com.bridgelabz.bookstorecartmodel.util;

import com.bridgelabz.bookstorecartmodel.dto.BookDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponse {
	private int errorcode;
	private String message;
	private BookDTO token;
	
	public BookResponse() {
		
	}
}
