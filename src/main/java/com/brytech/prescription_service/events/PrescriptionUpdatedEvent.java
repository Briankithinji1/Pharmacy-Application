package com.brytech.prescription_service.events;

import java.time.Instant;

import com.brytech.prescription_service.enums.PrescriptionStatus;

public record PrescriptionUpdatedEvent(
        Long prescriptionId,
        PrescriptionStatus oldStatus,
        PrescriptionStatus newStatus,
        Instant updatedAt
        ) {
}
