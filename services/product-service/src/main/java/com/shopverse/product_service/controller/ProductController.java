package com.shopverse.product_service.controller;

import java.math.BigDecimal;
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

import com.shopverse.product_service.dto.ProductRequest;
import com.shopverse.product_service.dto.ProductResponse;
import com.shopverse.product_service.dto.UpdatePriceRequest;
import com.shopverse.product_service.dto.UpdateProductDetailsRequest;
import com.shopverse.product_service.dto.UpdateStockRequest;
import com.shopverse.product_service.enums.ProductCategory;
import com.shopverse.product_service.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {
private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
		ProductResponse response = productService.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
		ProductResponse response = productService.getProductById(id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/active")
	public ResponseEntity<List<ProductResponse>> getActiveProducts() {
		List<ProductResponse> response = productService.getActiveProducts();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/category/{productCategory}")
	public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable ProductCategory productCategory) {
		List<ProductResponse> response = productService.getProductsByCategory(productCategory);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<ProductResponse>> getProductsContainingName(@PathVariable String name) {
		List<ProductResponse> response = productService.getProductsContainingName(name);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/priceRange")
	public ResponseEntity<List<ProductResponse>> getProductsByPriceRange(@RequestParam(required = true) BigDecimal low, @RequestParam(required = true) BigDecimal high) {
		List<ProductResponse> response = productService.getProductsByPriceRange(low, high);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/inStockGreaterThan/{quantity}")
	public ResponseEntity<List<ProductResponse>> getInStockProductsGreaterThan(@PathVariable Long quantity) {
		List<ProductResponse> response = productService.getInStockProductsGreaterThan(quantity);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/outOfStock")
	public ResponseEntity<List<ProductResponse>> getOutOfStockProducts() {
		List<ProductResponse> response = productService.getOutOfStockProducts();
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductDetailsRequest request) {
		ProductResponse response = productService.updateProduct(id, request);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}/price")
	public ResponseEntity<ProductResponse> updatePrice(@PathVariable Long id, @Valid @RequestBody UpdatePriceRequest newPrice) {
		ProductResponse response = productService.updatePrice(id, newPrice);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}/reduce")
	public ResponseEntity<Void> reduceStock(@PathVariable Long id, @Valid @RequestBody UpdateStockRequest request) {
		productService.reduceStock(id, request);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}/increase")
	public ResponseEntity<Void> increaseStock(@PathVariable Long id, @Valid @RequestBody UpdateStockRequest request) {
		productService.increaseStock(id, request);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}/deactivate")
	public ResponseEntity<Void> deactivateProduct(@PathVariable Long id) {
		productService.deactivateProduct(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}/activate")
	public ResponseEntity<Void> activateProduct(@PathVariable Long id) {
		productService.activateProduct(id);
		return ResponseEntity.noContent().build();
	}
}
