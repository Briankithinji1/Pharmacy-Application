package com.brytech.customer_service.dto;

import java.time.Instant;
import java.util.UUID;

public record OutboxDTO(
        UUID id,
        UUID aggregate_id,
        String aggregate_type,
        String event_type,
        String payload,
        Instant created_at,
        Instant sent_at,
        String status
) {
}
