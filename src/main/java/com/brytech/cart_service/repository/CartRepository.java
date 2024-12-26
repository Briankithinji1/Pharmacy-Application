package com.brytech.cart_service.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.brytech.cart_service.enumeration.CartStatus;
import com.brytech.cart_service.model.Cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCustomerId(Long id);

    @Query("SELECT c FROM Cart c WHERE c.customerId = :customerId AND c.cartStatus = 'ACTIVE'")
    Optional<Cart> findActiveCartByCustomerId(@Param("customerId") Long customerId);

    boolean existsByCustomerIdAndCartStatus(Long customerId, CartStatus status);
    List<Cart> findAllByCustomerId(Long customerId);
    Page<Cart> findByCartStatus(CartStatus status, Pageable pageable);
    List<Cart> findByLastUpdatedBefore(Instant timestamp);
    long countByCartStatus(CartStatus status);
}
