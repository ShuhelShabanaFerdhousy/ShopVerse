package com.shopverse.payment_service.controller;

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

import com.shopverse.common.enums.PaymentStatus;
import com.shopverse.payment_service.dto.PaymentRequest;
import com.shopverse.payment_service.dto.PaymentResponse;
import com.shopverse.payment_service.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payments")
@Validated
public class PaymentController {
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	@GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentsByOrderId(@PathVariable Long orderId) {
		PaymentResponse response = paymentService.getPaymentsByOrderId(orderId);
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long paymentId) {
		PaymentResponse response = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(response);
    }
	
	@PatchMapping("/{paymentId}/status")
	public ResponseEntity<PaymentResponse> updatePaymentStatus(@PathVariable Long paymentId, @RequestParam PaymentStatus status) {
	    PaymentResponse response = paymentService.updatePaymentStatus(paymentId, status);
	    return ResponseEntity.ok(response);
	}
}
