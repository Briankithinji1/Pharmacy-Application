package com.brytech.product_service.event;

import java.time.Instant;

public record ProductAvailabilityChangedEvent(
        Long productId,
        boolean isAvailable,
        Instant updatedAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
       return productId; 
    }
}
