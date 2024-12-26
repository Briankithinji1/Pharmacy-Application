package com.brytech.cart_service.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record CartItemDTO(
        Long cartItemId,
        Long productId,
        int quantity,
        BigDecimal price,
        Instant addedAt,
        Instant updatedAt
        ) {
}
