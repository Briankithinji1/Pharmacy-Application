package com.example.Pharmacy.Application.config.password;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword
) {
}
