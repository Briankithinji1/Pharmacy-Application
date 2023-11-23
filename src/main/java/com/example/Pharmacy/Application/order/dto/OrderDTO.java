package com.example.Pharmacy.Application.order.dto;

import com.example.Pharmacy.Application.order.enums.OrderStatus;
import com.example.Pharmacy.Application.product.ProductDTO;

import java.util.List;

public record OrderDTO(
        Long orderId,
//        String orderName,
//        String description,
        OrderStatus status,
        Integer quantity,
        Double totalPrice
) {}
