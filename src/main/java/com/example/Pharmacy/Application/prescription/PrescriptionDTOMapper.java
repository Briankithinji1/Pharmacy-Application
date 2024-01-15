package com.example.Pharmacy.Application.prescription;

import com.example.Pharmacy.Application.product.Product;
import com.example.Pharmacy.Application.product.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PrescriptionDTOMapper implements Function<Prescription, PrescriptionDTO> {

    @Override
    public PrescriptionDTO apply(Prescription prescription) {
        ProductDTO productDTO = mapProductToDTO(prescription.getProduct());

        return new PrescriptionDTO(
                prescription.getPrescriptionId(),
                prescription.getDosage(),
                prescription.getInstructionsOfUse(),
                prescription.getUsageDuration(),
                prescription.getPrescriptionFileId(),
                prescription.getCustomer().getUserId(),
                productDTO
        );
    }

    private ProductDTO mapProductToDTO(Product product) {
        if (product == null) {
            return null; // Adjust if necessary, depending on your requirements
        }

        return new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getImageUri(),
                product.getCategory().getCategoryName(),
                product.isAvailable()
        );
    }
}
