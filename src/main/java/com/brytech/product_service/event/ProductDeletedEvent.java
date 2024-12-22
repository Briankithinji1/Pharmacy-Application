package com.brytech.product_service.event;

import java.time.Instant;

public record ProductDeletedEvent(
        Long productId,
        Instant deletedAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return productId;
    }


}
