package com.example.Pharmacy.Application.cart.dto;

import jakarta.validation.constraints.NotNull;

public record AddToCartDto(
        Long id,
        @NotNull Long productId,
        @NotNull Integer quantity
) {
    @Override
    public String toString() {
        return "CartDto{" +
                "Id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ",";
    }
}
