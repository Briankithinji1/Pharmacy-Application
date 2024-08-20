package com.brytech.messaging_service.dto;

import com.brytech.messaging_service.enums.MessageStatus;

import java.time.Instant;
import java.util.Date;

public record MessageDto(
        String id,
        String senderId,
        String receiverId,
        String content,
        Instant timestamp,
        MessageStatus status
) {
}
