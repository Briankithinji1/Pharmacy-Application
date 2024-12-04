package com.brytech.pharmacist_service.event;

import java.time.Instant;

public record InventoryUpdatedEvent(
        Long branchId,
        Long drugId,
        Integer updatedQuantity,
        Instant updatedAt
        ) {
}
