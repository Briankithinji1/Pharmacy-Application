package com.example.Pharmacy.Application.cart;

import com.example.Pharmacy.Application.product.ProductDTO;

import java.util.List;

public record CartDTO(
        Long cartId,
        //Long userId,
        //String productName,
        String productDescription,
        Double productPrice,
        Integer quantity,
        Double totalPrice,
        String status,
        List<ProductDTO> products
) {}
