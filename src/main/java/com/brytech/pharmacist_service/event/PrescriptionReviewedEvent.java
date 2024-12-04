package com.brytech.pharmacist_service.event;

import java.time.LocalDateTime;

import com.brytech.pharmacist_service.enumeration.ReviewStatus;

public record PrescriptionReviewedEvent(
        Long prescriptionId,
        Long pharmacistId,
        ReviewStatus status,
        String notes,
        LocalDateTime reviewedAt
        ) {
}
