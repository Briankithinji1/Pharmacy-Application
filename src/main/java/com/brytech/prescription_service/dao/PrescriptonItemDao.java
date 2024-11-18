package com.brytech.prescription_service.dao;

import java.util.List;

import com.brytech.prescription_service.models.PrescriptionItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrescriptonItemDao {

    List<PrescriptionItem> saveAll(List<PrescriptionItem> items);
    Page<PrescriptionItem> findByPrescriptionId(Long prescriptionId, Pageable pageable);
    void deleteByPrescriptionId(Long prescriptionId);
}
