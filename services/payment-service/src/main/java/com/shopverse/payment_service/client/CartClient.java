package com.shopverse.payment_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.shopverse.payment_service.exception.CartNotFoundException;
import com.shopverse.payment_service.exception.CartServiceUnavailableException;

@Component
public class CartClient {
	private final WebClient webClient;

    @Value("${cart.service.url}")
    private String cartServiceUrl;

    public CartClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void clearCart(Long userId) {
        try {
            webClient.delete()
                    .uri(cartServiceUrl + "/users/" + userId + "/cart/items")
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class).map(error -> new CartNotFoundException("Cart not found for user: " + userId)))
                    .onStatus(status -> status.is5xxServerError(), response -> response.bodyToMono(String.class).map(error -> new CartServiceUnavailableException("Cart service unavailable")))
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception ex) {
            throw new CartServiceUnavailableException("Cart service is unreachable");
        }
    }
}
