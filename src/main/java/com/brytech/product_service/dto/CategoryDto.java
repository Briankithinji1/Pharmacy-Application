package com.brytech.product_service.dto;

import java.util.List;

public record CategoryDto(
        Long id,
        String name, 
        String imageUrl,
        String description,
        Long parentCategoryId,
        List<CategoryDto> subCategories
) {
    /***
    public static CategoryDto fromEntity(Category category) {
        return new CategoryDto(
            category.getCategoryId(),
            category.getName(),
            category.getImageUrl(),
            category.getDescription(),
            category.getSubCategories()
                .stream()
                .map(CategoryDto::fromEntity)
                .toList()
        );
    }
    ***/
}
