package com.brytech.pharmacist_service.event;

import java.time.Instant;

public record StockDispensedEvent(
        Long productId,
        int quantityDispensed,
        int remainingStock,
        Instant dispensedAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return productId;
    }
}
