package main.java.com.example.Pharmacy.Application.payment.dto;

import lombok.Builder;

@Builder
public record RegisterUrlRequest(
        String ShortCode,
        String ResponseType,
        String ConfirmationURL,
        String ValidationURL
) { }
