package com.brytech.cart_service.event;

import java.time.Instant;

public record CartCheckedOutEvent(
        Long cartId, 
        Long userId, 
        Instant checkedOutAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return cartId;
    }
}
