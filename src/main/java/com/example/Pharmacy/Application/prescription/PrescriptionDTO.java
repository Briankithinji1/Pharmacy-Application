package com.example.Pharmacy.Application.prescription;

import com.example.Pharmacy.Application.product.ProductDTO;
import com.example.Pharmacy.Application.user.model.Customer;

import java.util.List;

public record PrescriptionDTO(
        Long prescriptionId,
        String dosage,
        String instructionsOfUse,
        String usageDuration,
        String prescriptionFileId,
        Long userId,
        List<ProductDTO> products
) {
}
