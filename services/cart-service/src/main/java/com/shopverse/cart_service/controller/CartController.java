package com.shopverse.cart_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopverse.cart_service.dto.AddToCartRequest;
import com.shopverse.cart_service.dto.CartResponse;
import com.shopverse.cart_service.dto.UpdateCartItemRequest;
import com.shopverse.cart_service.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{userId}/cart")
@Validated
public class CartController {
private final CartService cartService;
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@GetMapping
	public ResponseEntity<CartResponse> getOrCreateActiveCart(@PathVariable Long userId) {
		CartResponse response = cartService.getOrCreateActiveCart(userId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CartResponse>> getAllCarts(@PathVariable Long userId) {
	    List<CartResponse> response = cartService.getAllCartsByUser(userId);
	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{cartId}")
	public ResponseEntity<CartResponse> getCartById(@PathVariable Long cartId) {
	    CartResponse response = cartService.getCartById(cartId);
	    return ResponseEntity.ok(response);
	}
	
	@PostMapping("/items")
	public ResponseEntity<CartResponse> addItemToCart(@PathVariable Long userId, @RequestBody @Valid AddToCartRequest request) {
		CartResponse response = cartService.addItemToCart(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PatchMapping("/items/{productId}")
    public ResponseEntity<CartResponse> updateCartItem(@PathVariable Long userId, @PathVariable Long productId, @Valid @RequestBody UpdateCartItemRequest request) {
        CartResponse response = cartService.updateCartItem(userId, productId, request);
        return ResponseEntity.ok(response);
    }
	
	@DeleteMapping("/items/{productId}")
	public ResponseEntity<Void> removeCartItem(@PathVariable Long userId, @PathVariable Long productId) {
		cartService.removeCartItem(userId, productId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/items")
	public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
		cartService.clearCart(userId);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/checkout")
	public ResponseEntity<CartResponse> checkoutCart(@PathVariable Long userId) {
		CartResponse response = cartService.checkoutCart(userId);
		return ResponseEntity.ok(response);
	}
}
