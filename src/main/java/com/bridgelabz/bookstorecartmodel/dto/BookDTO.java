package com.bridgelabz.bookstorecartmodel.dto;

import lombok.Data;

@Data
public class BookDTO {
	private long bookId;
	private long userId;
	private String bookName;
	private String bookAuthor;
	private String bookDescription;
	private double bookPrice;
	private int bookQuantity;
}
