package com.brytech.prescription_service.dto;

import java.time.Instant;
import java.util.List;

import com.brytech.prescription_service.enums.PrescriptionStatus;

public record PrescriptionDto(
        Long id,
        String prescriptionNumber,
        PrescriptionStatus status,
        Instant createdAt,
        Instant updatedAt,
        boolean uploadedByPatient,
        Long customerId,
        List<PrescriptionItemDTO> items
        ) {
}
