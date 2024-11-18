package com.brytech.prescription_service.dto;

public record PrescriptionItemDTO(
        Long id,
        String medicineName,
        String dosage,
        int quantity,
        String dosageInstructions
        ) {
}
