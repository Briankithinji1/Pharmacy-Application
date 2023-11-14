package com.example.Pharmacy.Application.product;

public record ProductDTO(
        Long productId,
        String productName,
        String description,
        Double price,
        String quantity,
        String imageUri,
        String categoryName,
        boolean isAvailable
) {
}
