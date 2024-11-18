package com.brytech.prescription_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.prescription_service.models.PrescriptionReview;
import com.brytech.prescription_service.repository.PrescriptionReviewRepository;

import org.springframework.stereotype.Repository;

@Repository("reviewJpa")
public class PrescriptionReviewJpaDataAccessService implements PrescriptionReviewDao {

    private final PrescriptionReviewRepository repository;

    public PrescriptionReviewJpaDataAccessService(PrescriptionReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PrescriptionReview> findByPrescriptionId(Long prescriptionId) {
        if (prescriptionId == null) {
            throw new IllegalArgumentException("Prescription ID must not be null");
        }
        return repository.findByPrescriptionId(prescriptionId);
    }

    @Override
    public List<PrescriptionReview> findByReviewerId(Long pharmacistId) {
        if (pharmacistId == null) {
            throw new IllegalArgumentException("Pharmacist ID must not be null");
        }
        return repository.findByReviewerId(pharmacistId);
    }

    @Override
    public Optional<PrescriptionReview> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("PrescriptionReview ID must not be null");
        }
        return repository.findById(id);
    }

    @Override
    public PrescriptionReview save(PrescriptionReview review) {
        if (review == null) {
            throw new IllegalArgumentException("PrescriptionReview must not be null");
        }
        return repository.save(review);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Prescription ID must not be null");
        }
        repository.deleteById(id);
    }    
}
