package com.brytech.prescription_service.dao;

import java.util.Optional;

import com.brytech.prescription_service.models.Prescription;
import com.brytech.prescription_service.repository.PrescriptionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("prescriptionJpa")
public class PrescriptionJpaDataAccessService implements PrescriptionDao {

    private final PrescriptionRepository repository;

    public PrescriptionJpaDataAccessService(PrescriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Prescription save(Prescription prescription) {
        return repository.save(prescription);
    }

    @Override
    public Optional<Prescription> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Prescription> findByStatus(String status, Pageable pageable) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Prescription Status must not be null or empty");
        }
        return repository.findByStatus(status, pageable);
    }

    @Override
    public Page<Prescription> findByCustomerId(Long customerId, Pageable pageable) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }
        return repository.findByCustomerId(customerId, pageable);
    }

    @Override
    public Prescription update(Prescription prescription) {
        return repository.save(prescription);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
