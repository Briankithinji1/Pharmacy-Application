package com.brytech.pharmacist_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    private Long productId;
    private String productName;
    private String imageUri;
    private Double price;
    private String unit;
    private Integer stock;
    private String category;
    private String description;
}
