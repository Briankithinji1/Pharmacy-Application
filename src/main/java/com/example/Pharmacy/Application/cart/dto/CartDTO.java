package com.example.Pharmacy.Application.cart.dto;

import java.util.List;

public record CartDTO(
        List<CartItemDTO> cartItems,
        Double totalPrice
) {}
