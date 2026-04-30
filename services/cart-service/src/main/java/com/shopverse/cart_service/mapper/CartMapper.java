package com.shopverse.cart_service.mapper;

import java.util.ArrayList;
import java.util.List;

import com.shopverse.cart_service.dto.CartItemResponse;
import com.shopverse.cart_service.dto.CartResponse;
import com.shopverse.cart_service.entity.Cart;

public class CartMapper {
	public static CartResponse toResponse(Cart cart) {
		CartResponse response = new CartResponse();
		
		List<CartItemResponse> items = new ArrayList<>();
		cart.getItems().forEach(item -> items.add(CartItemMapper.toResponse(item)));
		
		response.setId(cart.getId());
		response.setUserId(cart.getUserId());
		response.setCartStatus(cart.getCartStatus());
		response.setTotalAmount(cart.getTotalAmount());
		response.setItems(items);
		response.setCreatedAt(cart.getCreatedAt());
		response.setUpdatedAt(cart.getUpdatedAt());
		
		return response;
	}
}
