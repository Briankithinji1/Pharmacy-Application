package com.brytech.cart_service.dao;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.brytech.cart_service.enumeration.CartStatus;
import com.brytech.cart_service.model.Cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartDao {

    Cart save(Cart cart);
    Optional<Cart> findById(Long id);
    Optional<Cart> findByCustomerId(Long id);
    Optional<Cart> findActiveCartByCustomerId(Long customerId);
    boolean existsByCustomerIdAndCartStatus(Long customerId, CartStatus status);
    List<Cart> findAllByCustomerId(Long customerId);
    Page<Cart> findByCartStatus(CartStatus status, Pageable pageable);
    List<Cart> findByLastUpdatedBefore(Instant timestamp);
    long countByCartStatus(CartStatus status);
    void deleteCart(Long id);
}
