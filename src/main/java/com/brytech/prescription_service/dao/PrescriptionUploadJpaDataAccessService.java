package com.brytech.prescription_service.dao;

import java.util.List;
import java.util.Optional;

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
        return uploadRepository.save(upload);
    }

    @Override
    public List<PrescriptionUpload> findByCustomerId(Long customerId) {
        return uploadRepository.findByCustomerId(customerId);
    }

    @Override
    public List<PrescriptionUpload> findByLinkedPrescriptionId(Long prescriptionId) {
        return uploadRepository.findByLinkedPrescriptionId(prescriptionId);
    }

    @Override
    public int updateStatus(Long id, String status) {
        return uploadRepository.updateStatus(id, status);
    }

    @Override
    public List<PrescriptionUpload> findByStatus(String status) {
        return uploadRepository.findByStatus(status);
    }

    @Override
    public void deleteById(Long id) {
        uploadRepository.deleteById(id);
    }

    @Override
    public Optional<PrescriptionUpload> findById(Long id) {
        return uploadRepository.findById(id);
    }
}
