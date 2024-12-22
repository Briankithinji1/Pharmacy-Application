package com.brytech.product_service.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.brytech.product_service.model.Category;
import com.brytech.product_service.model.Product;
import com.brytech.product_service.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("productJpa")
public class ProductJpaDataAccessService implements ProductDao {

    private final ProductRepository productRepository;

    public ProductJpaDataAccessService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Page<Product> findByCategory(Category category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable);
    }

    @Override
    public Page<Product> findByIsAvailableTrue(Pageable pageable) {
        return productRepository.findProductsByAvailableTrue(pageable);
    }

    @Override
    public Page<Product> findByProductCategoryIn(List<Category> categories, Pageable pageable) {
        return productRepository.findProductsByCategories(categories, pageable);
    }

    @Override
    public Page<Product> findByUnitPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findByUnitPriceBetween(minPrice, maxPrice, pageable);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }    
}
