package com.brytech.pharmacist_service.dto;

public record PharmacyBranchDto(
        Long branchId,
        String branchName,
        String branchLocation
) {
}
