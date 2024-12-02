package com.brytech.prescription_service.dto;

public record PrescriptionItemDTO(
        Long id,
        String medicineName,
        String dosage,
        String frequency,
        String instructions,
        int quantity,
        PrescriptionDto prescription
        ) {
}
