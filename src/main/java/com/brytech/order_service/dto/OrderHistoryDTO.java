package com.brytech.order_service.dto;

import java.time.LocalDateTime;

import com.brytech.order_service.enumeration.OrderStatus;

public record OrderHistoryDTO(
        Long id,
        OrderStatus status,
        LocalDateTime updatedAt,
        String updatedBy
) {
}
