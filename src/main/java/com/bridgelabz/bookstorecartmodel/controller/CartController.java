package com.bridgelabz.bookstorecartmodel.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<Response> addToCart(@RequestBody CartDTO cartDTO, @PathVariable Long bookId, @RequestHeader String token) {
		CartModel cartModel = cartService.addToCart(cartDTO, bookId, token);
		Response response = new Response(200, "Book added to cart", cartModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
}
