package com.brytech.pharmacist_service.event;

import java.time.Instant;

public record PrescriptionErrorEvent(
        Long prescriptionId,
        String errorMessage,
        Instant errorOccuredAt
        ) {
}
