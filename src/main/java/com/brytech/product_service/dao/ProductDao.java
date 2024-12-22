package com.brytech.product_service.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.brytech.product_service.model.Category;
import com.brytech.product_service.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao {

    Product save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    Page<Product> findByCategory(Category category, Pageable pageable);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByNameContaining(String name, Pageable pageable);
    Page<Product> findByIsAvailableTrue(Pageable pageable);
    Page<Product> findByProductCategoryIn(List<Category> categories, Pageable pageable);
    Page<Product> findByUnitPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Product update(Product product);
    void deleteById(Long id);
}
