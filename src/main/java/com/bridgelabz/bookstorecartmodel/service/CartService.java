package com.bridgelabz.bookstorecartmodel.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.bookstorecartmodel.util.BookResponse;
import com.bridgelabz.bookstorecartmodel.util.TokenUtil;
import com.bridgelabz.bookstorecartmodel.util.UserResponse;
import com.bridgelabz.bookstorecartmodel.dto.CartDTO;
import com.bridgelabz.bookstorecartmodel.exception.CartNotFoundException;
import com.bridgelabz.bookstorecartmodel.model.CartModel;
import com.bridgelabz.bookstorecartmodel.repository.CartRepository;

/**
 * Purpose:Service for cart(business logic)
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@Service
public class CartService implements ICartService {
	@Autowired
	CartRepository cartRepository;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public CartModel addToCart(CartDTO cartDTO, Long bookId, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BS-UserService:8090/userService/verifyToken/" + token, UserResponse.class);
		if (isUserPresent.getErrorcode() == 200) {
			BookResponse isBookPresent = restTemplate.getForObject("http://BS-BookModel:8091/bookService/validateBook/" + bookId, BookResponse.class);
			if (isBookPresent.getErrorcode() == 200) {
				CartModel cartModel = new CartModel(cartDTO);
				cartModel.setBookId(bookId);
				cartModel.setUserId(userId);
				if(isBookPresent.getToken().getBookQuantity() >= cartDTO.getQuantity()) {
					cartModel.setQuantity(cartDTO.getQuantity());
				} else {
					throw new CartNotFoundException(400, "Book not found");
				}
				cartModel.setTotalPrice((long) ((cartDTO.getQuantity()) * (isBookPresent.getToken().getBookPrice())));
				cartRepository.save(cartModel);
				return cartModel;
			}
		}
		throw new CartNotFoundException(400, "Books not found");
	}
}
