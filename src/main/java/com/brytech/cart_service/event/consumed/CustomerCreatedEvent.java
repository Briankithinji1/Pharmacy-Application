package com.brytech.cart_service.event.consumed;

import com.brytech.cart_service.event.AggregateEvent;

public record CustomerCreatedEvent(
        Long customerId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return customerId;
    }
}
