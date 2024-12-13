package com.brytech.order_service.event.outgoing;

import java.time.Instant;

public record OrderCompletedEvent(
        Long orderId,
        String orderReference,
        Long customerId,
        Instant completedAt
) {}
