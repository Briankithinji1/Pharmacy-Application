package com.brytech.order_service.event.incoming;

import java.time.Instant;

public record DeliveryScheduledEvent(
        Long orderId,
        Instant scheduledDeliveryTime,
        String deliveryAddress
) {}
