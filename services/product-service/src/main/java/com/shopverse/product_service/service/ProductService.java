package com.shopverse.product_service.service;

import java.math.BigDecimal;
import java.util.List;

import com.shopverse.common.event.StockReductionEvent;
import com.shopverse.product_service.dto.ProductRequest;
import com.shopverse.product_service.dto.ProductResponse;
import com.shopverse.product_service.dto.UpdatePriceRequest;
import com.shopverse.product_service.dto.UpdateProductDetailsRequest;
import com.shopverse.product_service.dto.UpdateStockRequest;
import com.shopverse.product_service.enums.ProductCategory;

public interface ProductService {
	ProductResponse createProduct(ProductRequest request);
	ProductResponse getProductById(Long id);
	ProductResponse updateProduct(Long id, UpdateProductDetailsRequest request);
	ProductResponse updatePrice(Long id, UpdatePriceRequest request);
	List<ProductResponse> getActiveProducts();
	List<ProductResponse> getProductsByCategory(ProductCategory productCategory);
	List<ProductResponse> getProductsContainingName(String name);
	List<ProductResponse> getProductsByPriceRange(BigDecimal low, BigDecimal high);
	List<ProductResponse> getInStockProductsGreaterThan(Long quantity);
	List<ProductResponse> getOutOfStockProducts();
	void reduceStock(Long id, UpdateStockRequest request);
	void increaseStock(Long id, UpdateStockRequest request);
	void deactivateProduct(Long id);
	void activateProduct(Long id);
	void handleStockReduction(StockReductionEvent event);
}
