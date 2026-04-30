package com.shopverse.product_service.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopverse.product_service.entity.Product;
import com.shopverse.product_service.enums.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByIsActiveTrue();
	List<Product> findByProductCategory(ProductCategory productCategory);
	List<Product> findByNameContainingIgnoreCase(String name);
	boolean existsByNameIgnoreCase(String name);
	boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
	
	@Query("SELECT p FROM Product p WHERE p.price BETWEEN :low AND :high AND p.isActive = true")
	List<Product> findByPriceRange(@Param("low")BigDecimal low, @Param("high")BigDecimal high);
	
	List<Product> findByStockQuantityGreaterThanAndIsActive(Long quantity, boolean isActive);
	
	@Query("SELECT p FROM Product p WHERE p.stockQuantity = 0 AND p.isActive = :isActive")
	List<Product> findOutOfStockProducts(@Param("isActive") boolean isActive);
}
