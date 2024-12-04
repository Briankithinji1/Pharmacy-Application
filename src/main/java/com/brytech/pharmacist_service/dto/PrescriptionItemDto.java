package com.brytech.pharmacist_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrescriptionItemDto {
    private Long productId;
    private String productName;
    private Integer quantity;
    private String dosageInstructions;
}
