package com.brytech.pharmacist_service.repository;

import java.util.List;
import java.util.Optional;

import com.brytech.pharmacist_service.enumeration.ReviewStatus;
import com.brytech.pharmacist_service.model.PrescriptionReview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionReviewRepository extends JpaRepository<PrescriptionReview, Long> {

    Page<PrescriptionReview> findAll(Pageable pageable);
    Page<PrescriptionReview> findByStatus(ReviewStatus status, Pageable pageable);
    Optional<PrescriptionReview> findByPrescriptionId(Long prescriptionId);
    List<PrescriptionReview> findByPharmacistId(Long pharmacistId);

    boolean isPrescriptionReviewExistByPrescriptionId(Long prescriptionId);
}
