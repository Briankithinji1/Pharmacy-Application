package com.brytech.prescription_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.prescription_service.models.PrescriptionItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrescriptionItemDao {

    PrescriptionItem saveItem(PrescriptionItem item);
    List<PrescriptionItem> saveAll(List<PrescriptionItem> items);
    Optional<PrescriptionItem> findById(Long id);
    Page<PrescriptionItem> findByPrescriptionId(Long prescriptionId, Pageable pageable);
    List<PrescriptionItem> findByPrescriptionId(Long prescriptionId);
    List<PrescriptionItem> findByNameAndPrescriptionId(String name, Long prescriptionId);
    void deleteById(Long itemId);
    void deleteByPrescriptionId(Long prescriptionId);

    boolean existsByNameAndPrescriptionId(String name, Long prescriptionId);
    boolean existsById(Long id);
    long countByPrescriptionId(Long prescriptionId);
}
