package com.brytech.messaging_service.dto;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public record ConversationDto(
        String id,
        List<String> participantIds,
        Instant lastUpdated
) {
}
