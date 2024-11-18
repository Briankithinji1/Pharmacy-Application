package com.brytech.user_service.config.password;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword
) {
}
