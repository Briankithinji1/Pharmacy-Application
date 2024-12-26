package com.brytech.cart_service.event.consumed;

import java.time.Instant;

import com.brytech.cart_service.event.AggregateEvent;

public record ProductDeletedEvent(
        Long productId,
        Instant deletedAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return productId;
    }
}
