package com.brytech.prescription_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.prescription_service.models.PrescriptionUpload;

public interface PrescriptionUploadDao {

    PrescriptionUpload save(PrescriptionUpload upload);
    Optional<PrescriptionUpload> findById(Long id);
    List<PrescriptionUpload> findByCustomerId(Long customerId);
    List<PrescriptionUpload> findByLinkedPrescriptionId(Long prescriptionId);
    int updateStatus(Long id, String status);
    List<PrescriptionUpload> findByStatus(String status);
    void deleteById(Long id);
}
