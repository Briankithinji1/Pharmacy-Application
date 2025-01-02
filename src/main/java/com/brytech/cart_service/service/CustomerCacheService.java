package com.brytech.cart_service.service;

import com.brytech.cart_service.event.consumed.CustomerCreatedEvent;
import com.brytech.cart_service.event.consumed.CustomerUpdatedEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerCacheService {

    private static final String CUSTOMER_CACHE_KEY = "customer";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void addCustomerToCache(Long customerId, CustomerCreatedEvent createdEvent) {
        redisTemplate.opsForHash().put(CUSTOMER_CACHE_KEY, customerId.toString(), createdEvent);
    }

    public CustomerCreatedEvent getCustomerById(Long customerId) {
        return (CustomerCreatedEvent) redisTemplate.opsForHash().get(CUSTOMER_CACHE_KEY, customerId);
    }

    public void updateCustomerInCache(Long customerId, CustomerUpdatedEvent updatedEvent) {
        redisTemplate.opsForHash().put(CUSTOMER_CACHE_KEY, customerId.toString(), updatedEvent);
    }

    public void removeCustomerFromCache(Long customerId) {
        redisTemplate.opsForHash().delete(CUSTOMER_CACHE_KEY, customerId);
    }
}
