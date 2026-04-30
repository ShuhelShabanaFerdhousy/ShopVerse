package com.shopverse.cart_service.dto;

import java.math.BigDecimal;

public class CartItemResponse {
	private Long id;
	private Long cartId;
	private Long productId;
	private String productName;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	private Integer quantity;
	
	public CartItemResponse() {}
	
	public CartItemResponse(Long id, Long cartId, Long productId, String productName, BigDecimal unitPrice, BigDecimal totalPrice, Integer quantity) {
		this.id = id;
		this.cartId = cartId;
		this.productId = productId;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.quantity = quantity;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
