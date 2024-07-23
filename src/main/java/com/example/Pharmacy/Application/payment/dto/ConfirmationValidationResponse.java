package main.java.com.example.Pharmacy.Application.payment.dto;

import lombok.Builder;

@Builder
public record ConfirmationValidationResponse(
        String ResultCode,
        String ResultDesc
) {
}
