package com.brytech.prescription_service.events;

import java.time.Instant;

public record PrescriptionUploadedEvent(
        Long prescriptionUploadId,
        Long customerId,
        String fileName,
        String fileType,    
        String fileUrl,
        String status,
        Instant uploadDate
        ) {
}
