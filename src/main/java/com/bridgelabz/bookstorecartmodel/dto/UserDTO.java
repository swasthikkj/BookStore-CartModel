package com.bridgelabz.bookstorecartmodel.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserDTO {
	private long id;
	private String firstName; 
	private String lastName; 
	private String dateOfBirth;
	private LocalDate registeredDate;
	private LocalDate updatedDate;
	private String password;
	private String emailId;
	private boolean verify;
	private int otp;
	private String profilePic;
	private LocalDate purchaseDate;
	private LocalDate expiryDate;

}
