package com.brytech.cart_service.event;

import java.time.Instant;

public record ItemAddedToCartEvent(
        Long cartId, 
        Long cartItemId, 
        Long productId, 
        int quantity, 
        Instant addedAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return cartId;
    }
}
