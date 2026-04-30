package com.shopverse.product_service.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopverse.common.event.StockItem;
import com.shopverse.common.event.StockReductionEvent;
import com.shopverse.product_service.dto.ProductRequest;
import com.shopverse.product_service.dto.ProductResponse;
import com.shopverse.product_service.dto.UpdatePriceRequest;
import com.shopverse.product_service.dto.UpdateProductDetailsRequest;
import com.shopverse.product_service.dto.UpdateStockRequest;
import com.shopverse.product_service.entity.Product;
import com.shopverse.product_service.enums.ProductCategory;
import com.shopverse.product_service.exception.InsufficientStockException;
import com.shopverse.product_service.exception.InvalidProductStateException;
import com.shopverse.product_service.exception.InvalidStockOperationException;
import com.shopverse.product_service.exception.ProductAlreadyExistsException;
import com.shopverse.product_service.exception.ProductNotFoundException;
import com.shopverse.product_service.mapper.ProductMapper;
import com.shopverse.product_service.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
private final ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	private Product getProductEntity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }
	
	@Override
	public ProductResponse createProduct(ProductRequest request) {
		if(productRepository.existsByNameIgnoreCase(request.getName())) {
			throw new ProductAlreadyExistsException("Product already exists.");
		}
		
		Product product = ProductMapper.toEntity(request);
		
		Product savedProduct = productRepository.save(product);
		return ProductMapper.toResponse(savedProduct);
	}
	
	@Transactional(readOnly = true)
	@Override
	public ProductResponse getProductById(Long id) {
		return ProductMapper.toResponse(getProductEntity(id));
	}
	
	@Override
	public ProductResponse updateProduct(Long id, UpdateProductDetailsRequest request) {
		Product product = getProductEntity(id);
		
		if(!product.isActive()) {
			throw new InvalidProductStateException("Product with id: " + id + " is inactive");
		}
		
		if (request.getName() != null) {
	        String newName = request.getName().trim();

	        if (!newName.equalsIgnoreCase(product.getName())) {
	            if (productRepository.existsByNameIgnoreCaseAndIdNot(newName, id)) {
	                throw new ProductAlreadyExistsException("Product name already exists");
	            }
	            product.setName(newName);
	        }
	    }
		
		if (request.getDescription() != null) {
	        String newDescription = request.getDescription().trim();

	        if (!newDescription.equals(product.getDescription())) {
	            product.setDescription(newDescription);
	        }
	    }
		
		if (request.getProductCategory() != null) {
	        if (request.getProductCategory() != product.getProductCategory()) {
	            product.setProductCategory(request.getProductCategory());
	        }
	    }
		
		Product updatedProduct = productRepository.save(product);
		return ProductMapper.toResponse(updatedProduct);
	}
	
	@Override
	public ProductResponse updatePrice(Long id, UpdatePriceRequest request) {
		Product product = getProductEntity(id);
		
		if(!product.isActive()) {
			throw new InvalidProductStateException("Product with id: " + id + " is inactive");
		}

	    if (request == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
	    	throw new IllegalArgumentException("Price must be greater than zero");
	    }

	    product.setPrice(request.getPrice());
	    Product updatedProduct = productRepository.save(product);
	    return ProductMapper.toResponse(updatedProduct);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ProductResponse> getActiveProducts() {
		List<ProductResponse> response = new ArrayList<>();
		productRepository.findByIsActiveTrue().forEach(product -> response.add(ProductMapper.toResponse(product)));
		
		return response;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ProductResponse> getProductsByCategory(ProductCategory productCategory) {
		List<ProductResponse> response = new ArrayList<>();
		productRepository.findByProductCategory(productCategory).forEach(product -> response.add(ProductMapper.toResponse(product)));
		
		return response;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ProductResponse> getProductsContainingName(String name) {
		List<ProductResponse> response = new ArrayList<>();
		productRepository.findByNameContainingIgnoreCase(name).forEach(product -> response.add(ProductMapper.toResponse(product)));
		
		return response;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ProductResponse> getProductsByPriceRange(BigDecimal low, BigDecimal high) {
		List<ProductResponse> response = new ArrayList<>();
		productRepository.findByPriceRange(low, high).forEach(product -> response.add(ProductMapper.toResponse(product)));
		
		return response;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ProductResponse> getInStockProductsGreaterThan(Long quantity) {
		List<ProductResponse> response = new ArrayList<>();
		productRepository.findByStockQuantityGreaterThanAndIsActive(quantity, true).forEach(product -> response.add(ProductMapper.toResponse(product)));
		
		return response;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ProductResponse> getOutOfStockProducts() {
		List<ProductResponse> response = new ArrayList<>();
		productRepository.findOutOfStockProducts(true).forEach(product -> response.add(ProductMapper.toResponse(product)));
		
		return response;
	}
	
	@Override
	public void reduceStock(Long id, UpdateStockRequest request) {
		Product product = getProductEntity(id);
		
		if(!product.isActive()) {
		    throw new InvalidProductStateException("Product with id: " + id + " is inactive");
		}
		if(request.getQuantity() <= 0) {
		    throw new InvalidStockOperationException("Quantity must be greater than zero");
		}
		if(product.getStockQuantity() <= 0 || product.getStockQuantity() < request.getQuantity()) {
		    throw new InsufficientStockException("Stock is less than requested quantity");
		}
		
		product.setStockQuantity(product.getStockQuantity() - request.getQuantity());
		productRepository.save(product);
	}
	
	@Override
	public void increaseStock(Long id, UpdateStockRequest request) {
		Product product = getProductEntity(id);
		
		if(!product.isActive()) {
		    throw new InvalidProductStateException("Product with id: " + id + " is inactive");
		}
		if(request.getQuantity() <= 0) {
		    throw new InvalidStockOperationException("Quantity must be greater than zero");
		}
		
		product.setStockQuantity(product.getStockQuantity() + request.getQuantity());
		productRepository.save(product);
	}
	
	@Override
	public void deactivateProduct(Long id) {
		Product product = getProductEntity(id);
		if (!product.isActive()) {
            throw new InvalidProductStateException("Product is already inactive");
        }
		
		product.setActive(false);
		productRepository.save(product);
	}
	
	@Override
	public void activateProduct(Long id) {
		Product product = getProductEntity(id);
		if (product.isActive()) {
            throw new InvalidProductStateException("Product is already active");
        }
		
		product.setActive(true);
		productRepository.save(product);
	}
	
	@Override
	public void handleStockReduction(StockReductionEvent event) {
		List<StockItem> items = event.getItems();
		
		for(StockItem item: items) {
			UpdateStockRequest request = new UpdateStockRequest(item.getQuantity());
			reduceStock(item.getProductId(), request);
		}
	}
}
