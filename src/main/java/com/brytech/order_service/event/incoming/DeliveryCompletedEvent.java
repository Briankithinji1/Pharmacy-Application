package com.brytech.order_service.event.incoming;

import java.time.Instant;

public record DeliveryCompletedEvent(
        Long orderId,
        Instant deliveredAt,
        String trackingNumber
) {}
