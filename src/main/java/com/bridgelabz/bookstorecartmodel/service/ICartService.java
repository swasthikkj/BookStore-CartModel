package com.bridgelabz.bookstorecartmodel.service;

import com.bridgelabz.bookstorecartmodel.dto.CartDTO;
import com.bridgelabz.bookstorecartmodel.model.CartModel;

public interface ICartService {
	CartModel addToCart(CartDTO cartDTO, Long bookId, String token);
	
}
