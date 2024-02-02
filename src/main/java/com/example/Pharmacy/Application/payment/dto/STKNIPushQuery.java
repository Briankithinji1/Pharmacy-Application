package com.example.Pharmacy.Application.payment.dto;

public record STKNIPushQuery(
        String BusinessShortCode,
        String Password,
        String Timestamp,
        String CheckoutRequestID
) {
}
