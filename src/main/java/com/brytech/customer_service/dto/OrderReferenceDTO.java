package com.brytech.customer_service.dto;

import java.time.Instant;
import java.util.UUID;

public record OrderReferenceDTO(
        UUID order_id,
        UUID customer_id,
        String order_status,
        Instant order_date
) {
}
