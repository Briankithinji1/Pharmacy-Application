package com.example.Pharmacy.Application.payment.dto;

import lombok.Builder;

@Builder
public record RegisterUrlResponse(
        String OriginatorCoversationID,
        String ResponseCode,
        String ResponseDescription
) {
}
