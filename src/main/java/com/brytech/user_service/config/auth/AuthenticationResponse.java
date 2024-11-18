package com.brytech.user_service.config.auth;

public record AuthenticationResponse(
        String accessToken,
        String refreshToken
) {
}
