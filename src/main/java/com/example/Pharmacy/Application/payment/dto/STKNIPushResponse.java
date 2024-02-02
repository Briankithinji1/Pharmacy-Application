package com.example.Pharmacy.Application.payment.dto;

public record STKNIPushResponse(
        String MerchantRequestID,
        String CheckoutRequestID,
        int ResponseCode,
        String ResponseDescription,
        String CustomerMessage
) {
}
