package com.brytech.prescription_service.dao;

import java.util.List;

import com.brytech.prescription_service.models.PrescriptionUpload;
import com.brytech.prescription_service.repository.PrescriptionUploadRepository;

import org.springframework.stereotype.Repository;


@Repository("prescriptionUploadJpa")
public class PrescriptionUploadJpaDataAccessService implements PrescriptionUploadDao {

    private final PrescriptionUploadRepository uploadRepository;

    public PrescriptionUploadJpaDataAccessService(PrescriptionUploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    @Override
    public PrescriptionUpload save(PrescriptionUpload upload) {
        if (upload == null) {
            throw new IllegalArgumentException("PrescriptionUpload must not be null");
        }
        return uploadRepository.save(upload);
    }

    @Override
    public List<PrescriptionUpload> findbyCustomerId(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }
        return uploadRepository.findByCustomerId(customerId);
    }

    @Override
    public List<PrescriptionUpload> findByLinkedPrescriptionId(Long prescriptionId) {
        if (prescriptionId == null) {
            throw new IllegalArgumentException("Prescription ID must not be null");
        }
        return uploadRepository.findbyLinkedPrescriptionId(prescriptionId);
    }

    @Override
    public int updateStatus(Long id, String status) {
        if (id == null || status == null) {
            throw new IllegalArgumentException("ID and Status must not be null");
        }
        return uploadRepository.updateStatus(id, status);
    }

    @Override
    public List<PrescriptionUpload> findbyStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status must not be null");
        }
        return uploadRepository.findbyStatus(status);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Prescription ID must not be null");
        }
        uploadRepository.deleteById(id);
    }
}
    
