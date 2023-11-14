package com.example.Pharmacy.Application.category;

import com.example.Pharmacy.Application.product.ProductDTO;

import java.util.List;

public record CategoryDTO(
        Long categoryId,
        String categoryName,
        String description,
        String imageUri,
        List<ProductDTO> products
) {
}
