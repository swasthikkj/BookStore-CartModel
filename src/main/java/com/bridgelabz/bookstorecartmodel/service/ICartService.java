package com.bridgelabz.bookstorecartmodel.service;

import java.util.List;

import com.bridgelabz.bookstorecartmodel.dto.CartDTO;
import com.bridgelabz.bookstorecartmodel.model.CartModel;
import com.bridgelabz.bookstorecartmodel.util.CartResponse;
/**
 * Purpose:Interface for cart
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */
public interface ICartService {
	
	CartModel addToCart(CartDTO cartDTO, Long bookId, String token);

	CartModel removingFromCart(Long cartId, String token);

	CartModel updateQuantity(Long cartId, Long quantity, String token);

	List<CartModel> getAllCartItemsForUser(String token);

	List<CartModel> getAllCartItems(String token);

	CartResponse validateCart(Long cartId);
	
}
