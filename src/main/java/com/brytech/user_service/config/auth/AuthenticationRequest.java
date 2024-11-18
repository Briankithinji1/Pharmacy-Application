package com.brytech.user_service.config.auth;

public record AuthenticationRequest(
        String email,
        String password
) {

}
