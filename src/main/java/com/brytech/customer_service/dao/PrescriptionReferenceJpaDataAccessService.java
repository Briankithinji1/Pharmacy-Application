package com.brytech.customer_service.dao;

import com.brytech.customer_service.model.PrescriptionReference;
import com.brytech.customer_service.repository.PrescriptionReferenceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("prescriptionReferenceJpa")
public class PrescriptionReferenceJpaDataAccessService implements PrescriptionReferenceDAO{

    private final PrescriptionReferenceRepository repository;

    public PrescriptionReferenceJpaDataAccessService(PrescriptionReferenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createPrescriptionReference(PrescriptionReference prescriptionReference) {
        repository.save(prescriptionReference);
    }

    @Override
    public Optional<PrescriptionReference> getPrescriptionReferenceById(UUID prescriptionId) {
        return repository.findById(prescriptionId);
    }

    @Override
    public List<PrescriptionReference> getPrescriptionReferencesByCustomerId(UUID customerId, Pageable pageable) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }
        Page<PrescriptionReference> page = repository.findByCustomerId(customerId, pageable);
        return page.getContent();
    }

    @Override
    public void deletePrescriptionReference(UUID prescriptionId) {
        repository.deleteById(prescriptionId);
    }
}
