package com.example.Pharmacy.Application.payment.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record STKNIPushPayload(
        String BusinessShortCode,
        String Password,
        String Timestamp,
        String TransactionType,
        BigDecimal Amount,
        String PartyA,
        String PartyB,
        String PhoneNumber,
        String CallBackURL,
        String AccountReference,
        String TransactionDesc
) {
}
