package com.brytech.cart_service.event.consumed;

import java.math.BigDecimal;

import com.brytech.cart_service.event.AggregateEvent;

public record ProductCreatedEvent(
        Long productId,
        String productName,
        BigDecimal price,
        String category,
        String status
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return productId;
    }
}
