package main.java.com.example.Pharmacy.Application.order.dto;

import main.java.com.example.Pharmacy.Application.order.enums.OrderStatus;

public record OrderDTO(
        Long orderId,
        OrderStatus status,
        Integer quantity,
        Double totalPrice
) {}
