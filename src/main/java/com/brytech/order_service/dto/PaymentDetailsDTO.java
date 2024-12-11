package com.brytech.order_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.brytech.order_service.enumeration.PaymentStatus;

public record PaymentDetailsDTO(
        Long id,
        BigDecimal amount,
        String paymentMethod,
        PaymentStatus paymentStatus,
        LocalDateTime paymentDate
) {
}
