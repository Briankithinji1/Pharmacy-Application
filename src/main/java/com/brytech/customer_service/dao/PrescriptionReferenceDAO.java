package com.brytech.customer_service.dao;

import com.brytech.customer_service.model.PrescriptionReference;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrescriptionReferenceDAO {

    void createPrescriptionReference(PrescriptionReference prescriptionReference);
    Optional<PrescriptionReference> getPrescriptionReferenceById(UUID prescriptionId);
    List<PrescriptionReference> getPrescriptionReferencesByCustomerId(UUID customerId, Pageable pageable);
    void deletePrescriptionReference(UUID prescriptionId);
}
