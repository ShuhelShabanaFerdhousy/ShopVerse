package com.shopverse.order_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.shopverse.order_service.dto.CartResponse;
import com.shopverse.order_service.exception.CartNotFoundException;
import com.shopverse.order_service.exception.CartServiceUnavailableException;

@Component
public class CartClient {
	private final WebClient webClient;
	
	@Value("${cart.service.url}")
	private String cartServiceUrl;
	
	public CartClient(WebClient webClient) {
		this.webClient = webClient;
	}
	
	public CartResponse getCartByUserId(Long id) {
		try {
			return webClient.get()
					.uri(cartServiceUrl + "/users/" + id + "/cart")
					.retrieve()
					.onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class).map(error -> new CartNotFoundException("Cart not found for user: " + id)))
					.onStatus(status -> status.is5xxServerError(), response -> response.bodyToMono(String.class).map(error ->new CartServiceUnavailableException("Cart service unavailable") ))
					.bodyToMono(CartResponse.class)
					.block();
		} catch (Exception ex) {
			throw new CartServiceUnavailableException("Cart service is unreachable");
		}
	}
	
	public void clearCart(Long userId) {
	    webClient.delete()
	            .uri(cartServiceUrl + "/users/" + userId + "/cart/items")
	            .retrieve()
	            .bodyToMono(Void.class)
	            .block();
	}
}
