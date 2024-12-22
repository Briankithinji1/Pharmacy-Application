package com.brytech.product_service.event;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductPriceChangedEvent(
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
