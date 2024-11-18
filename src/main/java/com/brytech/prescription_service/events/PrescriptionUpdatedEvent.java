package com.brytech.prescription_service.events;

import java.time.Instant;

public record PrescriptionUpdatedEvent(
        Long prescriptionId,
        String oldStatus,
        String newStatus,
        Instant updatedAt
        ) {
}
