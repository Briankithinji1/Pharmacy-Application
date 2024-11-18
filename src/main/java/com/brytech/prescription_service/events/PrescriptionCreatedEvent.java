package com.brytech.prescription_service.events;

import java.time.Instant;

public record PrescriptionCreatedEvent(
        Long prescriptionId,
        Long customerId,
        String prescriptionNumber,
        String status,
        Instant createdAt
        ) {
}
