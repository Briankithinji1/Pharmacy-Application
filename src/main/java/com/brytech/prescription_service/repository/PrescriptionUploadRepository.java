package com.brytech.prescription_service.repository;

import java.util.List;

import com.brytech.prescription_service.models.PrescriptionUpload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PrescriptionUploadRepository extends JpaRepository<PrescriptionUpload, Long> {

    List<PrescriptionUpload> findByCustomerId(Long customerId);
    List<PrescriptionUpload> findbyLinkedPrescriptionId(Long prescriptionId);
    List<PrescriptionUpload> findbyStatus(String status);

    @Modifying
    @Query("UPDATE PrescriptionUpload pu SET pu.status = :status WHERE pu.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
