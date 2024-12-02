package com.brytech.prescription_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.prescription_service.models.Prescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrescriptionDao {

    Page<Prescription> selectAllPrescriptions(Pageable pageable);
    Prescription save(Prescription prescription);
    Optional<Prescription> findById(Long id);
    Page<Prescription> findByStatus(String status, Pageable pageable);
    Page<Prescription> findByCustomerId(Long customerId, Pageable pageable);
    Prescription update(Prescription prescription);
    void deleteById(Long id);

    // Existence checks
    boolean isPrescriptionExistById(Long prescriptionId);
    boolean isPrescriptionExistByCustomerId(Long customerId);
}
