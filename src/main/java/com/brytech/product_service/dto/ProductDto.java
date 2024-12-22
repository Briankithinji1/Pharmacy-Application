package com.brytech.product_service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String description,
        String manufacturer,
        String form, // e.g., Tablet, Capsule, Liquid
        String strength, // e.g., 500mg, 10ml
        BigDecimal unitPrice,
        String imageUrl,
        boolean isAvailable,
        Instant createdAt,
        Instant updatedAt,
        List<CategoryDto> productCategory
) {}
