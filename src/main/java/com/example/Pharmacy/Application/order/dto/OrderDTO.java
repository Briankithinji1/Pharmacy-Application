package com.example.Pharmacy.Application.order.dto;

import com.example.Pharmacy.Application.order.enums.OrderStatus;

public record OrderDTO(
        Long orderId,
        OrderStatus status,
        Integer quantity,
        Double totalPrice
) {}
