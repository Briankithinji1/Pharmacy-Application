package com.brytech.pharmacist_service.event.consumed_events;

import java.time.Instant;

import com.brytech.pharmacist_service.enumeration.PrescriptionStatus;

public record PrescriptionCreatedEvent(
        Long prescriptionId,
        Long customerId,
        String prescriptionNumber,
        PrescriptionStatus status,
        Instant createdAt
        ) {
}
