package com.brytech.prescription_service.events;

import java.time.Instant;

import com.brytech.prescription_service.enums.ReviewStatus;

public record PrescriptionReviewedEvent(
        Long reviewId,
        Long prescriptionId,
        Long reviewerId,
        ReviewStatus status,
        String comments,
        Instant reviewedAt
        ) {
}
