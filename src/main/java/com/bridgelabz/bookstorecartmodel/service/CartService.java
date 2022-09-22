package com.bridgelabz.bookstorecartmodel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.bookstorecartmodel.util.BookResponse;
import com.bridgelabz.bookstorecartmodel.util.TokenUtil;
import com.bridgelabz.bookstorecartmodel.util.UserResponse;

import lombok.extern.slf4j.Slf4j;

import com.bridgelabz.bookstorecartmodel.dto.BookDTO;
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
@Slf4j
public class CartService implements ICartService {
	@Autowired
	CartRepository cartRepository;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	RestTemplate restTemplate;
	
	/**
	 *  Purpose:To add items to cart
	 */
	
	@Override
	public CartModel addToCart(CartDTO cartDTO, Long bookId, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BS-UserService:8090/userService/verifyToken/" + userId, UserResponse.class);
		if (isUserPresent.getErrorcode() == 200) {
			log.info("a");
			BookResponse isBookPresent = restTemplate.getForObject("http://BS-BookModel:8091/bookService/validateBook/" + bookId, BookResponse.class);
			if (isBookPresent.getErrorcode() == 200) {
				log.info("a");
				CartModel cartModel = new CartModel(cartDTO);
				cartModel.setBookId(bookId);
				cartModel.setUserId(userId);
				if(isBookPresent.getToken().getBookQuantity() > cartDTO.getQuantity()) {
					cartModel.setQuantity(cartDTO.getQuantity());
					cartModel.setTotalPrice((cartDTO.getQuantity()) * (isBookPresent.getToken().getBookPrice()));
					cartRepository.save(cartModel);
					BookResponse isBookIdPresent = restTemplate.getForObject("http://BS-BookModel:8091/bookService/updateBookQuantity/"	+bookId+"/" + cartDTO.getQuantity(), BookResponse.class);
					return cartModel;
				}
				throw new CartNotFoundException(400, cartDTO.getQuantity()+"Books not found"+isBookPresent.getToken().getBookQuantity()+"Books found");
			}
			throw new CartNotFoundException(400, "Books not found");
		}
		throw new CartNotFoundException(400, "Token not found");
	}
	
	/**
	 *  Purpose:removing items from cart
	 */
	
	@Override
	public CartModel removingFromCart(Long cartId, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BS-UserService:8090/userService/verifyToken/" + userId, UserResponse.class);
		if (isUserPresent.getErrorcode() == 200) {
			Optional<CartModel> isCartPresent = cartRepository.findById(cartId);
			if (isCartPresent.isPresent()) {
				if (isCartPresent.get().getUserId() == userId) {
					cartRepository.delete(isCartPresent.get());
					BookResponse isBookPresent = restTemplate.getForObject("http://BS-BookModel:8091/bookService/updateBooksQuantity/" +isCartPresent.get().getBookId() +"/"+ isCartPresent.get().getQuantity(), BookResponse.class);
					return isCartPresent.get();
				}
			}
			throw new CartNotFoundException(400, "Cart Id not found");
		}
		throw new CartNotFoundException(400, "User not found");
	}
	
	/**
	 *  Purpose:To update quantity
	 */
	
	@Override
	public CartModel updateQuantity(Long cartId, Long quantity, String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BS-UserService:8090/userService/verifyToken/" + userId, UserResponse.class);
		if (isUserPresent.getErrorcode() == 200) {
			Optional<CartModel> isCartPresent = cartRepository.findById(cartId);
			if (isCartPresent.isPresent()) {
				BookResponse isBookPresent = restTemplate.getForObject("http://BS-BookModel:8091/bookService/validateBook/" + isCartPresent.get().getBookId(), BookResponse.class);
				if (isCartPresent.get().getUserId() == userId) {
					if (isCartPresent.get().getQuantity() > quantity) {
						long bookQuantity = isCartPresent.get().getQuantity() - quantity;
						isCartPresent.get().setQuantity(quantity);
						isCartPresent.get().setTotalPrice(quantity * isBookPresent.getToken().getBookPrice());
						cartRepository.save(isCartPresent.get());
						BookResponse isBookIdPresent = restTemplate.getForObject("http://BS-BookModel:8091/bookService/updateBookQuantity/" + isCartPresent.get().getBookId() +"/"+ bookQuantity, BookResponse.class);
						return isCartPresent.get();
					} else {
						long bookQuantity = quantity-isCartPresent.get().getQuantity();
						isCartPresent.get().setQuantity(quantity);
						isCartPresent.get().setTotalPrice(quantity * isBookPresent.getToken().getBookPrice());
						cartRepository.save(isCartPresent.get());
						BookResponse isBookIdPresent = restTemplate.getForObject("http://BS-BookModel:8091/bookService/updateBooksQuantity/" + isCartPresent.get().getBookId() +"/"+ bookQuantity, BookResponse.class);
						return isCartPresent.get();
					}
				}
			}
			throw new CartNotFoundException(400, "Books not found");
		}
		throw new CartNotFoundException(400, "Token not found");
	}
	
	/**
	 *  Purpose:To fetch all cart item details for user 
	 */
	
	@Override
	public List<CartModel> getAllCartItemsForUser(String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BS-UserService:8090/userService/verifyToken/" + userId, UserResponse.class);
		if (isUserPresent.getErrorcode() == 200) {
			List<CartModel> isCartPresent = cartRepository.findByUserId(userId);
			if (isCartPresent.size()>0) {
				return isCartPresent;
			}
			throw new CartNotFoundException(500, "No items found in cart");
		}
		throw new CartNotFoundException(500, "Invalid token");
	}
	
	/**
	 *  Purpose:To fetch all cart item details 
	 */

	@Override
	public List<CartModel> getAllCartItems(String token) {
		Long userId = tokenUtil.decodeToken(token);
		UserResponse isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8049/bookstoreuser/validateuser/" + userId, UserResponse.class);
		if (isUserPresent.getErrorcode() == 200) {
			List<CartModel> isCartPresent = cartRepository.findAll();
			if (isCartPresent.size()>0) {
				return isCartPresent;
			}
			throw new CartNotFoundException(500, "No items found in cart");
		}
		throw new CartNotFoundException(500, "Invalid token");
	}
}
