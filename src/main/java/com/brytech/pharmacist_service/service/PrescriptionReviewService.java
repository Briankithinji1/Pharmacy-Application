package com.brytech.pharmacist_service.service;

import java.util.List;

import com.brytech.pharmacist_service.dao.PrescriptionReviewDao;
import com.brytech.pharmacist_service.dto.PrescriptionReviewDto;
import com.brytech.pharmacist_service.enumeration.ReviewStatus;
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

  //  @Transactional
//    public PrescriptionReviewDto createReview(PrescriptionReviewDto review) { }

    // TODO: UPDATE REVIEW METHOD


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
