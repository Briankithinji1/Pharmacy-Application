package com.brytech.pharmacist_service.dto;

import java.util.List;

public record PharmacistDto(
        Long id,
        String name,
        String email,
        String phoneNumber,
        PharmacyBranchDto branch,
        List<PrescriptionReviewDto> reviews
) {
}
