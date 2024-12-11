package com.brytech.order_service.dto;

import java.time.LocalDateTime;

import com.brytech.order_service.enumeration.DeliveryStatus;

public record DeliveryDetailsDTO(
        Long id,
        Long courierId,
        String deliveryAddress,
        LocalDateTime estimatedDeliveryTime,
        LocalDateTime deliveredAt,
        DeliveryStatus status
) {
}
