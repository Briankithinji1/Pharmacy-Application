package com.brytech.product_service.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.brytech.product_service.model.Category;
import com.brytech.product_service.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);
    Page<Product> findByCategory(Category category, Pageable pageable);
    Page<Product> findByNameContaining(String name, Pageable pageable);
    Page<Product> findProductsByAvailableTrue(Pageable pageable);
    Page<Product> findProductsByCategories(List<Category> categories, Pageable pageable);
    Page<Product> findByUnitPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
