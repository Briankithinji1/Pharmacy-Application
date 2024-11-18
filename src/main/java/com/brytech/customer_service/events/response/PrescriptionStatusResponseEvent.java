package com.brytech.customer_service.events.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionStatusResponseEvent {
    private UUID prescriptionId;
    private String status;
}
