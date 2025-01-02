package com.brytech.cart_service.event.consumed;

import com.brytech.cart_service.event.AggregateEvent;

public record CustomerDeletedEvent(
        Long customerId
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return customerId;
    }
}
