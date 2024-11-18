package com.brytech.customer_service.dto;

import java.time.Instant;
import java.util.UUID;

public record PrescriptionReferenceDTO(
        UUID prescription_id,
        UUID customer_id,
        Instant prescription_date
) {
}
