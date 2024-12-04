package com.brytech.pharmacist_service.event;

import java.time.Instant;

import com.brytech.pharmacist_service.dto.PrescriptionItemDto;

public record PrescriptionDispensedEvent(
        Long prescriptionId,
        Long pharmacistId,
        PrescriptionItemDto items,
        Instant dispensedAt
        ) {
}
