package com.brytech.cart_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.cart_service.model.CartItem;

public interface CartItemDao {

    CartItem save(CartItem cartItem);
    Optional<CartItem> findById(Long cartItemId);
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    List<CartItem> findByCartId(Long cartId);
    void delete(CartItem cartItem);
    void deleteById(Long cartItemId);
    void deleteAllByCartId(Long cartId);
    boolean existsByCartIdAndProductId(Long cartId, Long productId);
}
