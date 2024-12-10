package com.brytech.pharmacist_service.event;

import java.time.Instant;

public record PrescriptionRejectedEvent(
        Long prescriptionId,
        Long pharmacistId,
        String reason, // Reason for rejection
        Instant rejectedAt
) implements AggregateEvent {

    @Override
    public Long getAggregateId() {
        return prescriptionId;
    }
}
