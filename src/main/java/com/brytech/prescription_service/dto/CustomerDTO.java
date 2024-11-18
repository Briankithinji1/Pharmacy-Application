package com.brytech.prescription_service.dto;

import java.util.UUID;

public record CustomerDTO(
       UUID id,
       String first_name,
       String last_name,
       String email
        ) {
}
