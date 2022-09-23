package com.bridgelabz.bookstorecartmodel.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * Purpose:DTO for cart
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@Data
public class CartDTO {
	@Pattern(regexp = "^[1-1000]{1,4}$", message = "Quantity is Invalid")
	private long quantity;
}
