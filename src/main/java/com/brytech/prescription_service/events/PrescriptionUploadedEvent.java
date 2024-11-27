package com.brytech.prescription_service.events;

import java.time.Instant;

import com.brytech.prescription_service.enums.PrescriptionStatus;

public record PrescriptionUploadedEvent(
        Long prescriptionUploadId,
        Long customerId,
        String fileName,
        String fileType,    
        String fileUrl,
        PrescriptionStatus status,
        Instant uploadDate
        ) {
}
