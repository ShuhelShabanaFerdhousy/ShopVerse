package com.shopverse.order_service.enums;

public enum OrderStatus {
	CREATED,
    PAYMENT_PENDING,
    PAID,
    PAYMENT_FAILED,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
