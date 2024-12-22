package com.brytech.product_service.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.brytech.product_service.dao.CategoryDao;
import com.brytech.product_service.dao.ProductDao;
import com.brytech.product_service.dto.CategoryDto;
import com.brytech.product_service.dto.ProductDto;
import com.brytech.product_service.event.ProductCreatedEvent;
import com.brytech.product_service.event.ProductDeletedEvent;
import com.brytech.product_service.event.ProductEventPublisher;
import com.brytech.product_service.event.ProductUpdatedEvent;
import com.brytech.product_service.exception.RequestValidationException;
import com.brytech.product_service.exception.ResourceNotFoundException;
import com.brytech.product_service.model.Category;
import com.brytech.product_service.model.Product;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final CategoryDao categoryDao;
    private final ProductEventPublisher productEventPublisher;
    private final ModelMapper mapper;

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);

        if (productDto.productCategory() != null && !productDto.productCategory().isEmpty()) {
            List<Long> categoryIds = productDto.productCategory().stream()
                .map(CategoryDto::id)
                .collect(Collectors.toList());
            List<Category> categories = categoryDao.findAllById(categoryIds);
            if (categories.size() != categoryIds.size()) {
                List<Long> foundIds = categories.stream().map(Category::getCategoryId).toList();
                List<Long> notFoundIds = categoryIds.stream().filter(id -> !foundIds.contains(id)).toList();
                throw new RequestValidationException("Some categories not found with provided IDs: " + notFoundIds);
            }
            product.setProductCategory(categories);
        }

        product = productDao.save(product);

        ProductCreatedEvent event = new ProductCreatedEvent(
                product.getId(), product.getName(), product.getDescription(),
                product.getManufacturer(), product.getForm(), product.getStrength(),
                product.getUnitPrice(), product.getImageUrl(), product.isAvailable(), product.getCreatedAt()
        );
        productEventPublisher.publishProductCreatedEvent(event);

        ProductDto createdProduct = convertToDTO(product);

        return createdProduct;
    }

    public ProductDto findProductById(Long id) {
        return productDao.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with ID [%s] not found".formatted(id)
            ));
    }

    public ProductDto findProductByName(String name) {
        return productDao.findByName(name)
            .map(this::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with name [%s] not found".formatted(name)
            ));
    }

    public Page<ProductDto> findAllProducts(Pageable pageable) {
        return productDao.findAll(pageable)
                .map(this::convertToDTO);
    }

    public Page<ProductDto> findProductByNameContaining(String name, Pageable pageable) {
        if (name == null || name.trim().isEmpty()) {
            throw new RequestValidationException("Name cannot be null or empty");
        }

        return productDao.findByNameContaining(name, pageable)
            .map(this::convertToDTO);
    }

    public Page<ProductDto> findAvailableProducts(Pageable pageable) {
        return productDao.findByIsAvailableTrue(pageable)
            .map(this::convertToDTO);
    }

    public Page<ProductDto> findProductByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        if (minPrice == null || maxPrice == null || minPrice.compareTo(maxPrice) > 0) {
            throw new RequestValidationException("Invalid price range");
        }

        return productDao.findByUnitPriceBetween(minPrice, maxPrice, pageable)
            .map(this::convertToDTO);
    }

    public Page<ProductDto> findProductCategoryIn(List<Category> categories, Pageable pageable) {
        if (categories == null || categories.isEmpty()) {
            throw new RequestValidationException("Category list cannot be null or empty");
        }

        return productDao.findByProductCategoryIn(categories, pageable)
            .map(this::convertToDTO);
    }
    
    public Page<ProductDto> findProductByCategory(Category category, Pageable pageable) {
        if (category == null) {
            throw new RequestValidationException("Category cannot be null");
        }

        return productDao.findByCategory(category, pageable)
            .map(this::convertToDTO);
    }

    @Transactional
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product existingProduct = productDao.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID [%s] not found".formatted(productId)));

        convertToEntity(productDto);
        existingProduct.setUpdatedAt(Instant.now());

        if (productDto.productCategory() != null) {
            List<Long> categoryIds = productDto.productCategory().stream()
                    .map(CategoryDto::id)
                    .toList();
            List<Category> categories = categoryDao.findAllById(categoryIds);

            if (categories.size() != categoryIds.size()) {
                List<Long> foundIds = categories.stream().map(Category::getCategoryId).toList();
                List<Long> notFoundIds = categoryIds.stream().filter(id -> !foundIds.contains(id)).toList();
                throw new RequestValidationException("Some categories not found with provided IDs: " + notFoundIds);
            }
            existingProduct.setProductCategory(categories);
        } else {
            existingProduct.setProductCategory(Collections.emptyList());
        }

        Product updatedProduct = productDao.save(existingProduct);

        ProductUpdatedEvent event = ProductUpdatedEvent.builder()
                .productId(updatedProduct.getId())
                .name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .manufacturer(updatedProduct.getManufacturer())
                .form(updatedProduct.getForm())
                .strength(updatedProduct.getStrength())
                .unitPrice(updatedProduct.getUnitPrice())
                .imageUrl(updatedProduct.getImageUrl())
                .isAvailable(updatedProduct.isAvailable())
                .updatedAt(Instant.now())
                .build();
        productEventPublisher.publishProductUpdatedEvent(event);


        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Product with ID [%s] not found".formatted(id)
            ));

        productDao.deleteById(id);

        ProductDeletedEvent event = new ProductDeletedEvent(id, Instant.now());
        productEventPublisher.publishProductDeletedEvent(event); // Call the publisher directly
    }

    private ProductDto convertToDTO(Product product) {
        return mapper.map(product, ProductDto.class);
    }

    private Product convertToEntity(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }
}
