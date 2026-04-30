package com.shopverse.order_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopverse.order_service.dto.OrderRequest;
import com.shopverse.order_service.dto.OrderResponse;
import com.shopverse.order_service.dto.UpdateOrderStatusRequest;
import com.shopverse.order_service.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
	
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
		OrderResponse response = orderService.createOrder(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
		OrderResponse response = orderService.getOrderById(orderId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<List<OrderResponse>> getOrdersByUser(@RequestParam Long userId) {
		List<OrderResponse> response = orderService.getOrdersByUser(userId);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{orderId}/status")
	public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest request) {
		OrderResponse response = orderService.updateOrderStatus(orderId, request.getOrderStatus());
		return ResponseEntity.ok(response);
	}
}
