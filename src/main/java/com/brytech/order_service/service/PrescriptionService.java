package com.brytech.order_service.service;

import com.brytech.order_service.event.incoming.PrescriptionValidatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    public Long validatePrescription(PrescriptionValidatedEvent event) {
        return event.prescriptionId();
    }
}
