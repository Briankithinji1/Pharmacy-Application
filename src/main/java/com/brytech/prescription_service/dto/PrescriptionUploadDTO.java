package com.brytech.prescription_service.dto;

import java.time.Instant;

import com.brytech.prescription_service.enums.PrescriptionStatus;

public record PrescriptionUploadDTO(
       Long id,
       String fileName,
       String fileType,
       String fileUrl,
       PrescriptionStatus status,
       Instant uploadDate,
       Long customerId,
       Long linkedPrescriptionId
        ) {
}
