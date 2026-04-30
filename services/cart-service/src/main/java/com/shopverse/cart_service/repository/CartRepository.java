package com.shopverse.cart_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopverse.cart_service.entity.Cart;
import com.shopverse.cart_service.enums.CartStatus;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByUserIdAndCartStatus(Long userId, CartStatus cartStatus);
	List<Cart> findByUserId(Long userId);
}
