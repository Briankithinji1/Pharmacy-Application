package com.brytech.pharmacist_service.dto;

import java.time.Instant;

import com.brytech.pharmacist_service.enumeration.ReviewStatus;

public record PrescriptionReviewDto(
        Long reviewId,
        Long prescriptionId,
        ReviewStatus status,
        String notes,
        Instant reviewedAt
) {
}
