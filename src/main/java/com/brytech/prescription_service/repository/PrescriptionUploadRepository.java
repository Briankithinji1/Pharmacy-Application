package com.brytech.prescription_service.repository;

import java.util.List;

import com.brytech.prescription_service.enums.PrescriptionStatus;
import com.brytech.prescription_service.models.PrescriptionUpload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PrescriptionUploadRepository extends JpaRepository<PrescriptionUpload, Long> {

    List<PrescriptionUpload> findByCustomerId(Long customerId);
    List<PrescriptionUpload> findByLinkedPrescriptionId(Long prescriptionId);
    List<PrescriptionUpload> findByStatus(String status);

    boolean existsByFileNameAndCustomerId(String fileName, Long customerId);

    @Modifying
    @Query("UPDATE PrescriptionUpload pu SET pu.status = :status WHERE pu.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") PrescriptionStatus status);
}
