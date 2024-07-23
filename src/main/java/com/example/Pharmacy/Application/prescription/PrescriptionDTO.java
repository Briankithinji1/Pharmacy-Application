package main.java.com.example.Pharmacy.Application.prescription;

import main.java.com.example.Pharmacy.Application.product.ProductDTO;

public record PrescriptionDTO(
        Long prescriptionId,
        String dosage,
        String instructionsOfUse,
        String usageDuration,
        String prescriptionFileId,
        Long userId,
//        List<ProductDTO> products
        ProductDTO products
) {
}
