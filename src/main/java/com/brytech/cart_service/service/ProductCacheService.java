package com.brytech.cart_service.service;

import java.math.BigDecimal;

import com.brytech.cart_service.event.consumed.ProductCreatedEvent;
import com.brytech.cart_service.event.consumed.ProductUpdatedEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCacheService {

    private static final String PRODUCT_CACHE_KEY = "product";
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void addProductToCache(Long productId, ProductCreatedEvent productEvent) {
        redisTemplate.opsForHash().put(PRODUCT_CACHE_KEY, productId.toString(), productEvent);
    }

    public ProductCreatedEvent getProductById(Long productId) {
        return (ProductCreatedEvent) redisTemplate.opsForHash().get(PRODUCT_CACHE_KEY, productId.toString());
    }

    public ProductUpdatedEvent getProductPrice(BigDecimal price) {
        return (ProductUpdatedEvent) redisTemplate.opsForHash().get(PRODUCT_CACHE_KEY, price.toString());
    }

    public void updateProductInCache(Long productId, ProductUpdatedEvent updatedEvent) {
        redisTemplate.opsForHash().put(PRODUCT_CACHE_KEY, productId.toString(), updatedEvent);
    }

    public void removeProductFromCache(Long productId) {
        redisTemplate.opsForHash().delete(PRODUCT_CACHE_KEY, productId.toString());
    }
}
