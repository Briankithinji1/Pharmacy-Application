package com.brytech.cart_service.event.consumed;

import java.math.BigDecimal;
import java.time.Instant;

import com.brytech.cart_service.event.AggregateEvent;

public record ProductPriceUpdatedEvent(
        Long productId,
        BigDecimal oldPrice,
        BigDecimal newPrice,
        Instant updatedAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return productId;
    }
}
