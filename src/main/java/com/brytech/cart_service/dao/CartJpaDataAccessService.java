package com.brytech.cart_service.dao;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.brytech.cart_service.enumeration.CartStatus;
import com.brytech.cart_service.model.Cart;
import com.brytech.cart_service.repository.CartRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("cartJpa")
public class CartJpaDataAccessService implements CartDao {

    private final CartRepository cartRepository;

    public CartJpaDataAccessService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Optional<Cart> findByCustomerId(Long id) {
        return cartRepository.findByCustomerId(id);
    }

    @Override
    public Optional<Cart> findActiveCartByCustomerId(Long customerId) {
        return cartRepository.findActiveCartByCustomerId(customerId);
    }

    @Override
    public boolean existsByCustomerIdAndCartStatus(Long customerId, CartStatus status) {
        return cartRepository.existsByCustomerIdAndCartStatus(customerId, status);
    }

    @Override
    public List<Cart> findAllByCustomerId(Long customerId) {
        return cartRepository.findAllByCustomerId(customerId);
    }

    @Override
    public Page<Cart> findByCartStatus(CartStatus status, Pageable pageable) {
        return cartRepository.findByCartStatus(status, pageable);
    }

    @Override
    public List<Cart> findByLastUpdatedBefore(Instant timestamp) {
        return cartRepository.findByLastUpdatedBefore(timestamp);
    }

    @Override
    public long countByCartStatus(CartStatus status) {
        return cartRepository.countByCartStatus(status);
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }    
}
