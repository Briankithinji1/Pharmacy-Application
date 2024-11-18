package com.brytech.prescription_service.dto;

import java.util.List;

public record PharmacistDTO(
       Long id,
       String name,
       String licenseNumber,
       String speciality,
       List<PrescriptionDto> prescriptions
        ) {
}
