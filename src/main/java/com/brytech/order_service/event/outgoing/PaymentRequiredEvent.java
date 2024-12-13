package com.brytech.order_service.event.outgoing;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentRequiredEvent(
        Long orderId,
        String orderReference,
        Long customerId,
        BigDecimal amount,
        String paymentMethod,
        Instant requestedAt
) {}
