package com.brytech.prescription_service.dao;

import java.util.List;

import com.brytech.prescription_service.models.PrescriptionUpload;

public interface PrescriptionUploadDao {

    PrescriptionUpload save(PrescriptionUpload upload);
    List<PrescriptionUpload> findbyCustomerId(Long customerId);
    List<PrescriptionUpload> findByLinkedPrescriptionId(Long prescriptionId);
    int updateStatus(Long id, String status);
    List<PrescriptionUpload> findbyStatus(String status);
    void deleteById(Long id);
}
