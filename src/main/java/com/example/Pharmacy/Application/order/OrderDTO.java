package com.example.Pharmacy.Application.order;

import com.example.Pharmacy.Application.product.ProductDTO;

import java.util.List;
import java.util.Set;

public record OrderDTO(
        Long orderId,
        String orderName,
        String description,
        String status,
//        Double productPrice,
        Integer quantity,
        Double totalPrice,
        List<ProductDTO> products
) {}
