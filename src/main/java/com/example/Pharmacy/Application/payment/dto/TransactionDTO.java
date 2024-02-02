package com.example.Pharmacy.Application.payment.dto;

import com.example.Pharmacy.Application.payment.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
        Long id,
        Long orderId,
        Long paymentId,
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
