package com.brytech.order_service.event.outgoing;

import java.time.Instant;
import java.util.List;

import com.brytech.order_service.model.OrderItem;

public record OrderUpdatedEvent(
        Long orderId,
        String orderReference,
        Long customerId,
        List<OrderItem> items,
        Instant updatedAt,
        String updatedBy
) {}
