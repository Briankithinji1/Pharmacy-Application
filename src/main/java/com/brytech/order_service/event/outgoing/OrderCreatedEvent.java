package com.brytech.order_service.event.outgoing;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.brytech.order_service.model.OrderItem;

public record OrderCreatedEvent(
        Long orderId,
        String orderReference,
        Long customerId,
        List<OrderItem> orderItems,
        BigDecimal totalAmount,
        Instant createdAt
) {}
