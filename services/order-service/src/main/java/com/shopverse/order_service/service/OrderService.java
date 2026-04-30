package com.shopverse.order_service.service;

import java.util.List;

import com.shopverse.common.event.PaymentResultEvent;
import com.shopverse.order_service.dto.OrderRequest;
import com.shopverse.order_service.dto.OrderResponse;
import com.shopverse.order_service.enums.OrderStatus;

public interface OrderService {
	OrderResponse createOrder(OrderRequest request);
	OrderResponse getOrderById(Long orderId);
	List<OrderResponse> getOrdersByUser(Long userId);
	OrderResponse updateOrderStatus(Long orderId, OrderStatus orderStatus);
	void handlePaymentResult(PaymentResultEvent event);
}
