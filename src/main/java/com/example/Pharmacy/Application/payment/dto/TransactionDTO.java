package main.java.com.example.Pharmacy.Application.payment.dto;

import main.java.com.example.Pharmacy.Application.payment.enums.TransactionStatus;

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
