package com.brytech.customer_service.dto;

import com.brytech.customer_service.enums.AccountStatus;

import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String first_name,
        String last_name,
        String email,
        String phone_number,
        String address,
        AccountStatus account_status
) {
}
