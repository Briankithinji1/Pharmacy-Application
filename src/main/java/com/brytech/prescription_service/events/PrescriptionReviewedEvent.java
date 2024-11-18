package com.brytech.prescription_service.events;

import java.time.Instant;

public record PrescriptionReviewedEvent(
        Long reviewId,
        Long prescriptionId,
        Long reviewerId,
        String status,
        String comments,
        Instant reviewedAt
        ) {
}
