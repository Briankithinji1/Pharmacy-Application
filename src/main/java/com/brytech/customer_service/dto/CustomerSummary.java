package com.brytech.customer_service.dto;

import java.util.UUID;

public record CustomerSummary(
        UUID customer_id,
        String first_name,
        String last_name
) {}
