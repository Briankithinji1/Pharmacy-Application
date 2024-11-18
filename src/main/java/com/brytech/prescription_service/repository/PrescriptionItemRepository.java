package com.brytech.prescription_service.repository;

import com.brytech.prescription_service.models.PrescriptionItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {
    
   Page<PrescriptionItem> findByPrescriptionId(Long prescriptionId, Pageable pageable);
   void deleteByPrescriptionId(Long prescriptionId);
}
