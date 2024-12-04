package com.brytech.pharmacist_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.pharmacist_service.enumeration.ReviewStatus;
import com.brytech.pharmacist_service.model.PrescriptionReview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrescriptionReviewDao {

    PrescriptionReview save(PrescriptionReview review);
    Optional<PrescriptionReview> findById(Long id);
    Page<PrescriptionReview> findAll(Pageable pageable);
    Page<PrescriptionReview> findByStatus(ReviewStatus status, Pageable pageable);
    List<PrescriptionReview> findByPharmacistId(Long pharmacistId);
    Optional<PrescriptionReview> findByPrescriptionId(Long prescriptionId);
    void deleteById(Long id);
}
