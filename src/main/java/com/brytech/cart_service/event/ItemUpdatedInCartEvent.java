package com.brytech.cart_service.event;

import java.time.Instant;

public record ItemUpdatedInCartEvent(
        Long cartId,
        Long cartItemId,
        Long productId,
        int quantity,
        Instant updatedAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return cartId;
    }
}
