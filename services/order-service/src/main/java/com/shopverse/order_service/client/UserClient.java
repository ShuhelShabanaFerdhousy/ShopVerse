package com.shopverse.order_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.shopverse.order_service.dto.UserResponse;
import com.shopverse.order_service.exception.UserNotFoundException;
import com.shopverse.order_service.exception.UserServiceUnavailableException;

@Component
public class UserClient {
	private final WebClient webClient;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public UserClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public UserResponse getUserById(Long id) {
        try {
            return webClient.get()
                    .uri(userServiceUrl + "/users/" + id)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class).map(err -> new UserNotFoundException("User not found: " + id)))
                    .onStatus(status -> status.is5xxServerError(), response -> response.bodyToMono(String.class).map(err -> new UserServiceUnavailableException("User service unavailable")))
                    .bodyToMono(UserResponse.class)
                    .block();
        } catch (UserNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new UserServiceUnavailableException("User service is unreachable");
        }
    }
}
