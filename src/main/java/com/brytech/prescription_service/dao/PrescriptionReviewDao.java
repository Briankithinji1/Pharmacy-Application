package com.brytech.prescription_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.prescription_service.models.PrescriptionReview;
import org.springframework.data.repository.query.Param;

public interface PrescriptionReviewDao {

    PrescriptionReview save(PrescriptionReview review);
    List<PrescriptionReview> findByPrescriptionUploadId(Long uploadId);
    List<PrescriptionReview> findByReviewerId(Long pharmacistId);
    Optional<PrescriptionReview> findById(Long id);
    void deleteById(Long id);
}
