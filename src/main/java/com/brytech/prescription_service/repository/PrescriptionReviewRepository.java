package com.brytech.prescription_service.repository;

import java.util.List;
import java.util.Optional;

import com.brytech.prescription_service.models.PrescriptionReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrescriptionReviewRepository extends JpaRepository<PrescriptionReview, Long> {

    @Query("SELECT pr FROM PrescriptionReview pr WHERE pr.prescription.id = :prescriptionId")
    List<PrescriptionReview> findByPrescriptionId(@Param("prescriptionId") Long prescriptionId);

    @Query("SELECT pr FROM PrescriptionReview pr WHERE pr.reviewer.id = :pharmacistId")
    List<PrescriptionReview> findByReviewerId(@Param("pharmacistId") Long pharmacistId);

    Optional<PrescriptionReview> findById(Long id);

    @Modifying
    @Query("UPDATE PrescriptionReview pr SET pr.deleted = true WHERE pr.id = :id")
    void softDelete(@Param("id") Long id);
}
