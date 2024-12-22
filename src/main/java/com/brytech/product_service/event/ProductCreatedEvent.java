package com.brytech.product_service.event;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductCreatedEvent(
        Long productId,
        String name,
        String description,
        String manufacturer,
        String form,
        String strength,
        BigDecimal unitPrice,
        String imageUrl,
        boolean isAvailable,
        Instant createdAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return productId;
    }

}
