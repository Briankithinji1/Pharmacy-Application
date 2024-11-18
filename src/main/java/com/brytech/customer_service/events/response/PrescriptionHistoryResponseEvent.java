package com.brytech.customer_service.events.response;

import com.brytech.customer_service.dto.PrescriptionReferenceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionHistoryResponseEvent {
    private UUID customerId;
    private List<PrescriptionReferenceDTO> prescriptions;
}
