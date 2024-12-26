package com.brytech.cart_service.repository;

import java.util.List;
import java.util.Optional;

import com.brytech.cart_service.model.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    List<CartItem> findByCartId(Long cartId);
    void deleteAllByCartId(Long cartId);
    boolean existsByCartIdAndProductId(Long cartId, Long productId);
}
