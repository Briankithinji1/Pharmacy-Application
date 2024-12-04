package com.brytech.pharmacist_service.dto;

import com.brytech.pharmacist_service.enumeration.ReviewStatus;

import java.time.LocalDateTime;

public record PrescriptionReviewDto(
        Long reviewId,
        Long prescriptionId,
        ReviewStatus status,
        String notes,
        LocalDateTime reviewedAt
) {
}
