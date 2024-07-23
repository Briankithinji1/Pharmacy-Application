package main.java.com.example.Pharmacy.Application.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartDTO(
        List<CartItemDTO> cartItems,
        Double totalPrice
) {}
