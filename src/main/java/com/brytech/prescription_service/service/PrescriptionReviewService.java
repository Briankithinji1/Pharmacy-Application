package com.brytech.prescription_service.service;

import java.util.List;

import com.brytech.prescription_service.dao.PrescriptionReviewDao;
import com.brytech.prescription_service.dto.PrescriptionReviewDTO;
import com.brytech.prescription_service.exceptions.RequestValidationException;
import com.brytech.prescription_service.exceptions.ResourceNotFoundException;
import com.brytech.prescription_service.models.Pharmacist;
import com.brytech.prescription_service.models.PrescriptionReview;
import com.brytech.prescription_service.models.PrescriptionUpload;
import com.brytech.prescription_service.repository.PharmacistRepository;
import com.brytech.prescription_service.repository.PrescriptionUploadRepository;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionReviewService {

    private final PrescriptionReviewDao reviewDao;
    private final ModelMapper mapper;
    private final PrescriptionUploadRepository uploadRepository;
    private final PharmacistRepository pharmacistRepository;// TODO: Change to dao if need be


    public List<PrescriptionReviewDTO> findReviewsByPrescriptionId(Long prescriptionId) {
        return reviewDao.findByPrescriptionId(prescriptionId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    public List<PrescriptionReviewDTO> findReviewsByReviewerId(Long pharmacistId) {
        return reviewDao.findByReviewerId(pharmacistId)
            .stream()
            .map(this::convertToDTO)
            .toList();
    }

    public PrescriptionReviewDTO findReviewById(Long reviewId) {
        return reviewDao.findById(reviewId)
            .map(this::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with Id [%s] not found".formatted(reviewId)
            ));
    }

    public PrescriptionReviewDTO createReview(Long pharmacistId, Long prescriptionUploadId, PrescriptionReviewDTO reviewDTO) {
        Pharmacist pharmacist = pharmacistRepository.findById(pharmacistId)
            .orElseThrow(() -> new ResourceNotFoundException("Pharmacist not found"));

        PrescriptionUpload upload = uploadRepository.findById(prescriptionUploadId)
            .orElseThrow(() -> new ResourceNotFoundException("Uploaded prescription not found"));

        PrescriptionReview review = new PrescriptionReview(pharmacist, upload);
        review.setReviewStatus(reviewDTO.reviewStatus());
        review.setComments(reviewDTO.comments());

        PrescriptionReview savedReview = reviewDao.save(review);
        return convertToDTO(savedReview);
    }

    public PrescriptionReviewDTO updateReview(Long reviewId, Long pharmacistId, PrescriptionReviewDTO reviewDTO) {
        PrescriptionReview existingReview = reviewDao.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with ID [%s] not found".formatted(reviewId)
            ));

        // Validate that the pharmacist owns this review
        if (!existingReview.getReviewer().getId().equals(pharmacistId)) {
            throw new RequestValidationException(
                    "You can only update your own reviews."
            );
        }

        existingReview.setComments(reviewDTO.comments());
        existingReview.setReviewStatus(reviewDTO.reviewStatus());
//        existingReview.setUpdatedAt(Instant.now());
       
        PrescriptionReview updatedReview = reviewDao.save(existingReview);
        return convertToDTO(updatedReview);
    }

    public void deleteReviewById(Long reviewId) {
        try {
            reviewDao.deleteById(reviewId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Review with ID [%s] does not exist".formatted(reviewId)
            );
        }
    }

    private PrescriptionReviewDTO convertToDTO(PrescriptionReview review) {
        return mapper.map(review, PrescriptionReviewDTO.class);
    }

    private PrescriptionReview convertToEntity(PrescriptionReviewDTO reviewDTO) {
        return mapper.map(reviewDTO, PrescriptionReview.class);
    }
}
