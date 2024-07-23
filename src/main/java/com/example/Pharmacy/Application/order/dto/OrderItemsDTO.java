package main.java.com.example.Pharmacy.Application.order.dto;

import jakarta.validation.constraints.NotNull;

public record OrderItemsDTO(
        @NotNull Long orderId,
        @NotNull Long productId,
        @NotNull int quantity,
        @NotNull double price
) {}
