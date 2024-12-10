package com.brytech.pharmacist_service.dto;

public record InventoryDto(
        Long productId,
        String productName,
        int availableStock,
        int lowStockThreshold
        ) {
}
