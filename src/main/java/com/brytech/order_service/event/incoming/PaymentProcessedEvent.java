package com.brytech.order_service.event.incoming;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentProcessedEvent(
        Long orderId,
        BigDecimal amountPaid,
        String paymentMethod,
        Instant processedAt
) {}
