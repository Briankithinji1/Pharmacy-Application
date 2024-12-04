package com.brytech.pharmacist_service.event;

import java.time.Instant;

public record DrugShortageEvent(
        Long productId,
        String drugName,
        Long branchId,
        Integer quantityShortage,
        Instant shortageDetectedAt
        ) {
}
