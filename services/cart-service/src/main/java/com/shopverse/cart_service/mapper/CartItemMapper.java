package com.shopverse.cart_service.mapper;

import java.math.BigDecimal;

import com.shopverse.cart_service.dto.AddToCartRequest;
import com.shopverse.cart_service.dto.CartItemResponse;
import com.shopverse.cart_service.entity.Cart;
import com.shopverse.cart_service.entity.CartItem;

public class CartItemMapper {
	public static CartItemResponse toResponse(CartItem item) {
		CartItemResponse response = new CartItemResponse();
		
		response.setId(item.getId());
		response.setCartId(item.getCart().getId());
		response.setProductId(item.getProductId());
		response.setProductName(item.getProductName());
		response.setUnitPrice(item.getPrice());
		response.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
		response.setQuantity(item.getQuantity());
		
		return response;
	}
	
	public static CartItem toEntity(AddToCartRequest request, Cart cart, String productName, BigDecimal price) {
		CartItem item = new CartItem();
		
		item.setCart(cart);
		item.setProductId(request.getProductId());
		item.setProductName(productName);
		item.setQuantity(request.getQuantity());
		item.setPrice(price);
		
		return item;
	}
}
