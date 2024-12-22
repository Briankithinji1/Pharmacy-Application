package com.brytech.product_service.event;

import java.time.Instant;

public record ProductCategoryRemovedEvent( 
        Long categoryId, 
        Long parentCategoryId,
        Instant timestamp
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return categoryId;
    }
}


