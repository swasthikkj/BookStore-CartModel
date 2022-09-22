package com.bridgelabz.bookstorecartmodel.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.bookstorecartmodel.dto.CartDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Purpose:Model for cart
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@Entity
@Table(name = "CartModel")
@Data
@NoArgsConstructor
public class CartModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long userId;
	private long bookId;
	private long quantity;
	private double totalPrice;
	
	public CartModel(CartDTO cartDTO) {
		this.quantity = cartDTO.getQuantity();
	}
}
