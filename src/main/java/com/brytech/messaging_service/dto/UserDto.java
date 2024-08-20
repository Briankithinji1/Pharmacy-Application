package com.brytech.messaging_service.dto;

import com.brytech.messaging_service.enums.UserRole;

public record UserDto(
        String id,
        String username,
        UserRole role,
        boolean onlineStatus
) {
}
