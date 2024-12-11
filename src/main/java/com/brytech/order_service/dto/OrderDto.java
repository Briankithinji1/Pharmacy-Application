package com.brytech.order_service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.brytech.order_service.enumeration.OrderStatus;

public record OrderDto(
        Long id,
        String orderReference,
        Long customerId,
        List<OrderItemDTO> items,
        PaymentDetailsDTO paymentDetails,
        DeliveryDetailsDTO deliveryDetails,
        List<OrderHistoryDTO> orderHistory,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        OrderStatus status
) {}
