package com.brytech.customer_service.events.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionHistoryRequestEvent {
    private UUID customerId;
}
