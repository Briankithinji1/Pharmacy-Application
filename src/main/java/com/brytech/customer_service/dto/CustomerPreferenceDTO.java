package com.brytech.customer_service.dto;

import com.brytech.customer_service.enums.PreferenceType;

import java.util.UUID;

public record CustomerPreferenceDTO(
        UUID preference_id,
        CustomerSummary customer,
        PreferenceType preference_type,
        String value
) {
}
