package com.brytech.prescription_service.exceptions;

import java.time.LocalDateTime;

public record ApiError(
        String path,
        String message,
        int status_code,
        LocalDateTime localDateTime
        ) {
}
