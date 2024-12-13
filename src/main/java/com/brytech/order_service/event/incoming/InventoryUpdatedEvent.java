package com.brytech.order_service.event.incoming;

import java.time.Instant;

public record InventoryUpdatedEvent(
        Long productId,
        Integer quantityAvailable,
        Instant updatedAt
) {}
