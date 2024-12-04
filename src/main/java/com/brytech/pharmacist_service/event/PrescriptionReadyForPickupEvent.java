package com.brytech.pharmacist_service.event;

import java.time.Instant;

public record PrescriptionReadyForPickupEvent(
        Long prescriptionId,
        Long pharmacistId,
        Instant readyAt
        ) {
}
