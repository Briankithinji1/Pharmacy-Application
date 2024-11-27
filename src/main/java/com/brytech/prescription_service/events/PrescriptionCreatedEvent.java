package com.brytech.prescription_service.events;

import java.time.Instant;

import com.brytech.prescription_service.enums.PrescriptionStatus;

public record PrescriptionCreatedEvent(
        Long prescriptionId,
        Long customerId,
        String prescriptionNumber,
        PrescriptionStatus status,
        Instant createdAt
        ) {
}
