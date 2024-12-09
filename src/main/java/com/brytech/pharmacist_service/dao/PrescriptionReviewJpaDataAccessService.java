package com.brytech.pharmacist_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.pharmacist_service.enumeration.ReviewStatus;
import com.brytech.pharmacist_service.model.PrescriptionReview;
import com.brytech.pharmacist_service.repository.PrescriptionReviewRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("reviewJpa")
public class PrescriptionReviewJpaDataAccessService implements PrescriptionReviewDao {

    private final PrescriptionReviewRepository reviewRepository;

    public PrescriptionReviewJpaDataAccessService(PrescriptionReviewRepository prescriptionReviewRepository) {
        this.reviewRepository = prescriptionReviewRepository;
    }

    @Override
    public PrescriptionReview save(PrescriptionReview review) {
        return reviewRepository.save(review);
    }

    @Override
    public Optional<PrescriptionReview> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Page<PrescriptionReview> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Override
    public Page<PrescriptionReview> findByStatus(ReviewStatus status, Pageable pageable) {
        return reviewRepository.findByStatus(status, pageable);
    }

    @Override
    public List<PrescriptionReview> findByPharmacistId(Long pharmacistId) {
        return reviewRepository.findByPharmacistId(pharmacistId);
    }

    @Override
    public Optional<PrescriptionReview> findByPrescriptionId(Long prescriptionId) {
        return reviewRepository.findByPrescriptionId(prescriptionId);
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public boolean isPrescriptionReviewExistByPrescriptionId(Long prescriptionId) {
        return reviewRepository.isPrescriptionReviewExistByPrescriptionId(prescriptionId);
    }
}
