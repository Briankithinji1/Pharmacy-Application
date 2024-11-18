package com.brytech.prescription_service.repository;

import com.brytech.prescription_service.models.Prescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Page<Prescription> findByStatus(String status, Pageable pageable);
    Page<Prescription> findByCustomerId(Long customerId, Pageable pageable);
}
