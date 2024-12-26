package com.brytech.cart_service.event;

import java.time.Instant;

public record CartCreatedEvent(
        Long cartId,
        Long customerId,
        Instant createdAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return cartId;
    }
}
