package com.example.Pharmacy.Application.cart.dto;

import com.example.Pharmacy.Application.cart.model.Cart;
import com.example.Pharmacy.Application.product.Product;
import jakarta.validation.constraints.NotNull;

public record CartItemDTO(
        Long cartItemId,
        @NotNull Integer quantity,
        @NotNull Product product

        ) {
    public CartItemDTO(Cart cart) {
        this(cart.getCartId(), cart.getQuantity(), cart.getProduct());
    }

    @Override
    public String toString() {
        return "CartItemDto{" +
                "itemId=" + cartItemId +
                ", quantity=" + quantity +
                ", productName=" + product.getProductName() +
                ", prescription=" + product.getPrescriptions() +
                '}';
    }
}
