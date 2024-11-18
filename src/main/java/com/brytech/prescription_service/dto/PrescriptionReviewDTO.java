package com.brytech.prescription_service.dto;

import java.time.Instant;

import com.brytech.prescription_service.enums.ReviewStatus;

public record PrescriptionReviewDTO(
       Long id,
       ReviewStatus reviewStatus,
       String comments,
       Instant reviewedAt,
       PrescriptionUploadDTO prescriptionUpload
        ) {
}
