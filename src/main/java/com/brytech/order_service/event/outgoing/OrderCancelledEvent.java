package com.brytech.order_service.event.outgoing;

import java.time.Instant;

public record OrderCancelledEvent(
        Long orderId,
        String orderReference,
        Long customerId,
        Instant cancelledAt,
        String cancelledBy,
        String reason
) {}
