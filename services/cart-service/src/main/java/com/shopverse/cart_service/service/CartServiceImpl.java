package com.shopverse.cart_service.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopverse.cart_service.client.ProductClient;
import com.shopverse.cart_service.client.UserClient;
import com.shopverse.cart_service.dto.AddToCartRequest;
import com.shopverse.cart_service.dto.CartResponse;
import com.shopverse.cart_service.dto.ProductResponse;
import com.shopverse.cart_service.dto.UpdateCartItemRequest;
import com.shopverse.cart_service.entity.Cart;
import com.shopverse.cart_service.entity.CartItem;
import com.shopverse.cart_service.enums.CartStatus;
import com.shopverse.cart_service.exception.CartItemNotFoundException;
import com.shopverse.cart_service.exception.CartNotFoundException;
import com.shopverse.cart_service.exception.InvalidCartOperationException;
import com.shopverse.cart_service.exception.ProductNotFoundException;
import com.shopverse.cart_service.mapper.CartItemMapper;
import com.shopverse.cart_service.mapper.CartMapper;
import com.shopverse.cart_service.repository.CartRepository;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;
	private final ProductClient productClient;
	private final UserClient userClient;
	
	public CartServiceImpl(CartRepository cartRepository, ProductClient productClient, UserClient userClient) {
		this.cartRepository = cartRepository;
		this.productClient = productClient;
		this.userClient = userClient;
	}
	
	private Cart getActiveCart(Long userId) {
	    return cartRepository.findByUserIdAndCartStatus(userId, CartStatus.ACTIVE).orElseGet(() -> {
	            Cart newCart = new Cart(userId, CartStatus.ACTIVE);
	            return cartRepository.save(newCart);
	        });
	}
	
	private Cart getExistingActiveCart(Long userId) {
	    return cartRepository.findByUserIdAndCartStatus(userId, CartStatus.ACTIVE).orElseThrow(() -> new CartNotFoundException("No active cart found for user: " + userId));
	}
	
	private void recalculateCartTotal(Cart cart) {
	    BigDecimal total = BigDecimal.ZERO;

	    for (CartItem item : cart.getItems()) {
	    	BigDecimal itemTotal = item.getPrice()
	                .multiply(BigDecimal.valueOf(item.getQuantity()));
	        total = total.add(itemTotal);
	    }

	    cart.setTotalAmount(total);
	}
	
	@Override
	public CartResponse getOrCreateActiveCart(Long userId) {
		userClient.getUserById(userId);
		
		Cart cart = getActiveCart(userId);
		return CartMapper.toResponse(cart);
	}
	
	@Override
	public List<CartResponse> getAllCartsByUser(Long userId) {
	    userClient.getUserById(userId);

	    List<Cart> carts = cartRepository.findByUserId(userId);
	    List<CartResponse> responses = new ArrayList<>();

	    for (Cart cart : carts) {
	        responses.add(CartMapper.toResponse(cart));
	    }

	    return responses;
	}
	
	@Override
	public CartResponse getCartById(Long cartId) {
	    Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

	    return CartMapper.toResponse(cart);
	}
	
	@Override
	public CartResponse addItemToCart(Long userId, AddToCartRequest request) {
		userClient.getUserById(userId);
		
		if (request.getQuantity() <= 0) {
		    throw new InvalidCartOperationException("Quantity must be greater than zero");
		}
		
		Cart cart = getActiveCart(userId);
		CartItem existingItem = null;
		
		for(CartItem item : cart.getItems()) {
			if(item.getProductId().equals(request.getProductId())) {
				existingItem = item;
				break;
			}
		}
		
		Cart savedCart = null;
		
		if(existingItem != null) {
			ProductResponse product = productClient.getProductById(request.getProductId());

		    if (existingItem.getQuantity() + request.getQuantity() > product.getStockQuantity()) {
		        throw new InvalidCartOperationException("Not enough stock for product: " + product.getName());
		    }

		    existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
		    savedCart = cartRepository.save(cart);
		} else {
			ProductResponse product = productClient.getProductById(request.getProductId());

		    if (request.getQuantity() > product.getStockQuantity()) {
		        throw new InvalidCartOperationException("Not enough stock for product: " + product.getName());
		    }

		    String productName = product.getName();
		    BigDecimal price = product.getPrice();

		    CartItem newItem = CartItemMapper.toEntity(request, cart, productName, price);
		    cart.getItems().add(newItem);
		    savedCart = cartRepository.save(cart);
		}
		
		recalculateCartTotal(savedCart);
		return CartMapper.toResponse(savedCart);
	}
	
	@Override
	public CartResponse updateCartItem(Long userId, Long productId, UpdateCartItemRequest request) {
		userClient.getUserById(userId);
		
		if (request.getQuantity() <= 0) {
		    throw new InvalidCartOperationException("Quantity must be greater than zero");
		}
		
		Cart cart = getExistingActiveCart(userId);
		List<CartItem> items = cart.getItems();
		
		CartItem updateItem = null;
		
		for(CartItem item : items) {
			if(item.getProductId().equals(productId)) {
				updateItem = item;
				break;
			}
		}
		
		if(updateItem == null) {
			throw new CartItemNotFoundException("No products found in the cart with the product id: " + productId);
		} else {
			ProductResponse product = productClient.getProductById(productId);

		    if (product == null) {
		        throw new ProductNotFoundException("Product not found: " + productId);
		    }

		    if (request.getQuantity() > product.getStockQuantity()) {
		        throw new InvalidCartOperationException("Not enough stock for product: " + product.getName());
		    }

		    updateItem.setQuantity(request.getQuantity());
		}
		
		recalculateCartTotal(cart);
		Cart savedCart = cartRepository.save(cart);
		return CartMapper.toResponse(savedCart);
	}
	
	@Override
	public void removeCartItem(Long userId, Long productId) {
		userClient.getUserById(userId);
		
		Cart cart = getExistingActiveCart(userId);
		
		CartItem removeItem = null;
		for(CartItem item : cart.getItems()) {
			if(item.getProductId().equals(productId)) {
				removeItem = item;
				break;
			}
		}
		
		if(removeItem == null) {
			throw new CartItemNotFoundException("No products found in the cart with the product id: " + productId);
		}
		
		cart.getItems().remove(removeItem);
		recalculateCartTotal(cart);
		cartRepository.save(cart);
	}
	
	@Override
	public void clearCart(Long userId) {
		userClient.getUserById(userId);
		
		Cart cart = getExistingActiveCart(userId);
		
		cart.getItems().clear();
		cart.setTotalAmount(BigDecimal.ZERO);
		cartRepository.save(cart);
	}
	
	@Override
	public CartResponse checkoutCart(Long userId) {
		userClient.getUserById(userId);
		
		Cart cart = getExistingActiveCart(userId);
		
		if (cart.getItems().isEmpty()) {
		    throw new InvalidCartOperationException("Cannot checkout an empty cart");
		}
		
		cart.setCartStatus(CartStatus.CHECKED_OUT);
		Cart savedCart = cartRepository.save(cart);
		return CartMapper.toResponse(savedCart);
	}
}
