package com.shopverse.order_service.mapper;

import java.util.ArrayList;
import java.util.List;

import com.shopverse.order_service.dto.OrderItemResponse;
import com.shopverse.order_service.dto.OrderResponse;
import com.shopverse.order_service.entity.Order;
import com.shopverse.order_service.entity.OrderItem;

public class OrderMapper {
	public static OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();

        List<OrderItemResponse> items = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setId(item.getId());
            itemResponse.setProductId(item.getProductId());
            itemResponse.setProductName(item.getProductName());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setQuantity(item.getQuantity());

            items.add(itemResponse);
        }

        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setOrderStatus(order.getOrderStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setItems(items);
        response.setPaymentMethod(order.getPaymentMethod());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());

        return response;
    }
}
