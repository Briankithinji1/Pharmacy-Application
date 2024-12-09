package com.brytech.pharmacist_service.service;

import java.util.List;

import jakarta.transaction.Transactional;

import com.brytech.pharmacist_service.dao.PrescriptionReviewDao;
import com.brytech.pharmacist_service.dto.PrescriptionReviewDto;
import com.brytech.pharmacist_service.enumeration.ReviewStatus;
import com.brytech.pharmacist_service.exception.DuplicateResourceException;
import com.brytech.pharmacist_service.exception.InvalidOperationException;
import com.brytech.pharmacist_service.exception.RequestValidationException;
import com.brytech.pharmacist_service.exception.ResourceNotFoundException;
import com.brytech.pharmacist_service.model.PrescriptionReview;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionReviewService {

    private final PrescriptionReviewDao reviewDao;
    private final ModelMapper mapper;

    @Transactional
    public PrescriptionReviewDto createReview(PrescriptionReviewDto reviewDto) {
        if (reviewDao.isPrescriptionReviewExistByPrescriptionId(reviewDto.prescriptionId())) {
            throw new DuplicateResourceException(
                    "Review for Prescription ID [%s] already exists.".formatted(reviewDto.prescriptionId())
            );
        }

        PrescriptionReview review = new PrescriptionReview();
        review.setPrescriptionId(reviewDto.prescriptionId());
        review.setReviewedAt(reviewDto.reviewedAt());
        review.setReviewStatus(ReviewStatus.PENDING);
        review.setNotes(reviewDto.notes());

        PrescriptionReview savedReview = reviewDao.save(review);

        return converToDto(savedReview);
    }

    @Transactional
    public PrescriptionReviewDto updateReview(Long reviewId, PrescriptionReviewDto reviewDto) {
        PrescriptionReview existingReview = reviewDao.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with ID [$s] not found.".formatted(reviewId)
            ));

        if (existingReview.getReviewStatus() == ReviewStatus.APPROVED && reviewDto.status() != ReviewStatus.APPROVED) {
            throw new InvalidOperationException(
                    "Cannot modify an approved review."
            );
        }

        if (reviewDto.status() != null) {
            existingReview.setReviewStatus(reviewDto.status());
        }

        if (reviewDto.notes() != null) {
            existingReview.setNotes(reviewDto.notes());
        }

        if (reviewDto.reviewedAt() != null) {
            existingReview.setReviewedAt(reviewDto.reviewedAt());
        }

        PrescriptionReview updatedReview = reviewDao.save(existingReview);

        return converToDto(updatedReview);
    }

    public PrescriptionReviewDto findReviewByid(Long id) {
        return reviewDao.findById(id)
            .map(this::converToDto)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with ID [%s] not found".formatted(id)
            ));
    }

    public Page<PrescriptionReviewDto> findAllReviews(Pageable pageable) {
        return reviewDao.findAll(pageable)
            .map(this::converToDto);
    }

    public Page<PrescriptionReviewDto> findReviewByStatus(ReviewStatus status, Pageable pageable) {
        return reviewDao.findByStatus(status, pageable)
            .map(this::converToDto);
    }

    public List<PrescriptionReviewDto> findByPharmacistId(Long pharmacistId) {
        if (pharmacistId == null) {
            throw new RequestValidationException("Pharmacist ID cannot be null");
        }

        return reviewDao.findByPharmacistId(pharmacistId)
            .stream()
            .map(this::converToDto)
            .toList();
    }

    public PrescriptionReviewDto findByPrescriptionId(Long prescriptionId) {
        if (prescriptionId == null) {
            throw new RequestValidationException("Prescription ID cannot be null");
        }

        return reviewDao.findByPrescriptionId(prescriptionId)
            .map(this::converToDto)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with Prescription ID [%s] not found".formatted(prescriptionId)
            ));
    }

    public void deleteById(Long id) {
        try {
            reviewDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Review with ID [%s] does not exist".formatted(id)
            );
        }
    }


    private PrescriptionReviewDto converToDto(PrescriptionReview review) {
        return mapper.map(review, PrescriptionReviewDto.class);
    }
}
