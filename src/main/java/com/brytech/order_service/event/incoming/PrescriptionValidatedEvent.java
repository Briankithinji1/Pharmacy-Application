package com.brytech.order_service.event.incoming;

import java.time.Instant;

public record PrescriptionValidatedEvent(
        Long orderId,
        Long prescriptionId,
        Instant validatedAt
) {}
