package com.bridgelabz.bookstorecartmodel.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstorecartmodel.dto.CartDTO;
import com.bridgelabz.bookstorecartmodel.model.CartModel;
import com.bridgelabz.bookstorecartmodel.service.ICartService;
import com.bridgelabz.bookstorecartmodel.util.Response;

/**
 * Purpose:Controller for cart
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@RestController
@RequestMapping("/cartModel")
public class CartController {
	@Autowired
	ICartService cartService;
	
	/**
	 * Purpose:add to cart
	 */

	@PostMapping("/addToCart")
	public ResponseEntity<Response> addToCart(@Valid @RequestBody CartDTO cartDTO, @RequestParam Long bookId, @RequestHeader String token) {
		CartModel cartModel = cartService.addToCart(cartDTO, bookId, token);
		Response response = new Response(200, "Book added to cart", cartModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	/**
	 * Purpose:remove book from cart
	 */
	
	@DeleteMapping("/removingFromCart/{cartId}")
	public ResponseEntity<Response> removingFromCart(@RequestParam Long cartId, @RequestHeader String token) {
		CartModel cartModel = cartService.removingFromCart(cartId, token);
		Response response = new Response(200, "Book removed from cart", cartModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	/**
	 * Purpose:updating book quantity in cart
	 */
	
	@PutMapping("/updateQuantity/{cartId}")
	public ResponseEntity<Response> updateQuantity(@Valid @RequestParam Long cartId, @PathVariable Long quantity, @RequestHeader String token) {
		CartModel cartModel = cartService.updateQuantity(cartId, quantity, token);
		Response response = new Response(200, "Book quantity updated in cart", cartModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	/**
	 * Purpose:fetching all cart items for user
	 */
	
	@GetMapping("/getAllCartItemsForUser")
	public ResponseEntity<Response> getAllCartItemsForUser(@RequestHeader String token) {
		List<CartModel> cartModel = cartService.getAllCartItemsForUser(token);
		Response response = new Response(200, "fetched all items in cart for user", cartModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	/**
	 * Purpose:fetching all cart items 
	 */
	
	@GetMapping("/getAllCartItems")
	public ResponseEntity<Response> getAllCartItems(@RequestHeader String token) {
		List<CartModel> cartModel = cartService.getAllCartItems(token);
		Response response = new Response(200, "fetched all items in cart", cartModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
}
