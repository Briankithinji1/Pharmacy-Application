package com.brytech.cart_service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.brytech.cart_service.enumeration.CartStatus;

public record CartDTO(
        Long cartId,
        Long customerId,
        BigDecimal totalPrice,
        CartStatus status,
        Instant createdAt,
        Instant updatedAt,
        List<CartItemDTO> cartItems
        ) {
}
