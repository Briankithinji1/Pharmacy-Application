package com.brytech.order_service.exception;

import java.time.LocalDateTime;

public record ApiError(
        String path,
        String message,
        int status_code,
        LocalDateTime localDateTime
        ) {
}
