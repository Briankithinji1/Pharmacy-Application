package com.example.Pharmacy.Application.payment.dto;

public record STKNIPushQueryResponse(
        String ResponseCode,
        String ResponseDescription,
        String MerchantRequestID,
        String CheckoutRequestID,
        String ResultCode,
        String ResultDesc
) {
}
