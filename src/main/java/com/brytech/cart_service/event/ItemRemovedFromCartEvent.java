package com.brytech.cart_service.event;

import java.time.Instant;

public record ItemRemovedFromCartEvent(
        Long cartId, 
        Long cartItemId, 
        Long productId,
        Instant removedAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return cartId;
    }
}
