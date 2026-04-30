package com.shopverse.cart_service.service;

import java.util.List;

import com.shopverse.cart_service.dto.AddToCartRequest;
import com.shopverse.cart_service.dto.CartResponse;
import com.shopverse.cart_service.dto.UpdateCartItemRequest;

public interface CartService {
	CartResponse getOrCreateActiveCart(Long userId);
	List<CartResponse> getAllCartsByUser(Long userId);
	CartResponse getCartById(Long cartId);
	CartResponse addItemToCart(Long userId, AddToCartRequest request);
	CartResponse updateCartItem(Long userId, Long productId, UpdateCartItemRequest request);
	void removeCartItem(Long userId, Long productId);
	void clearCart(Long userId);
	CartResponse checkoutCart(Long userId);
}
