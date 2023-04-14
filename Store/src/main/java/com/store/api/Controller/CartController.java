package com.store.api.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.Model.Pojo.Cart;
import com.store.api.Model.Request.CreateCart;
import com.store.api.Service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	@GetMapping("/cart")
	public ResponseEntity<List<Cart>> getAllCarts () {
		try {
			List<Cart> carts = cartService.getAllCarts();
			return ResponseEntity.status(HttpStatus.OK).body(carts);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/cart")
	public ResponseEntity<Cart> createCart(
			@RequestBody CreateCart request) {
		try {
			Cart cart = cartService.createCart(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(cart);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/cart/{cartId}/products/{productId}")
	public ResponseEntity<Cart> addProductToCart(
			@PathVariable String cartId,
			@PathVariable String productId) {
		try {
			Cart cart = cartService.addProductOnCart(cartId, productId);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/cart/{cartId}/products/{productId}")
	public ResponseEntity<Cart> deleteProductOnCart(
			@PathVariable String cartId,
			@PathVariable String productId) {
		try {
			Cart cart = cartService.deleteProductOnCart(cartId, productId);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}	
	
	@DeleteMapping("/cart/{cartId}")
	public ResponseEntity<Cart> deleteCart(
			@PathVariable String cartId) {
		try {
			Cart cart = cartService.deleteCart(cartId);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}	

}
