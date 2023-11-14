package com.example.Pharmacy.Application.prescription;

import com.example.Pharmacy.Application.product.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class PrescriptionDTOMapper implements Function<Prescription, PrescriptionDTO> {

    @Override
    public PrescriptionDTO apply(Prescription prescription) {
        List<ProductDTO> productDTOS = prescription.getMedicine()
                .stream()
                .map(product -> new ProductDTO(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getImageUri(),
//                        product.getCategory().getCategoryId(),
                        product.getCategory().getCategoryName(),
                        product.isAvailable()
                ))
                .toList();

        return new PrescriptionDTO(
                prescription.getPrescriptionId(),
                prescription.getDosage(),
                prescription.getInstructionsOfUse(),
                prescription.getUsageDuration(),
                prescription.getPrescriptionFileId(),
                prescription.getCustomer().getUserId(),
                productDTOS
        );
    }
}
