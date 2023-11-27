package com.example.Pharmacy.Application.config.auth;

public record AuthenticationResponse(
        String accessToken,
        String refreshToken
) {
}
