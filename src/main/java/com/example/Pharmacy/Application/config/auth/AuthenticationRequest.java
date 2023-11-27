package com.example.Pharmacy.Application.config.auth;

public record AuthenticationRequest(
        String email,
        String password
) {

}
