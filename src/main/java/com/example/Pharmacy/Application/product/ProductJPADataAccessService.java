package com.example.Pharmacy.Application.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class ProductJPADataAccessService implements ProductDao {

    private final ProductRepository productRepository;

    public ProductJPADataAccessService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> selectAllProducts() {
        Page<Product> page = productRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public List<Product> selectProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }

    @Override
    public Optional<Product> selectProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void insertProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public boolean isProductExistsById(Long productId) {
        return productRepository.existsProductByProductId(productId);
    }

    @Override
    public boolean existsProductByName(String productName) {
        return productRepository.existsProductByProductName(productName);
    }

    @Override
    public void updateProductQuantity(Long productId, int quantity) {
        productRepository.updateProductQuantityById(productId, quantity);
    }

    @Override
    public void updateProductAvailability(Long productId, boolean isAvailable) {
        productRepository.updateProductAvailabilityById(isAvailable, productId);
    }

    @Override
    public void updateProductPrice(Long productId, String price) {
        productRepository.updateProductPriceById(price, productId);
    }

    @Override
    public void updateProductImage(Long productId, String imageUri) {
        productRepository.updateProductImageById(imageUri, productId);
    }
}
