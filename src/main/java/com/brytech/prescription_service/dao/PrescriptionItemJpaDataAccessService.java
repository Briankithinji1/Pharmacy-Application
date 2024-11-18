package com.brytech.prescription_service.dao;

import java.util.List;

import com.brytech.prescription_service.models.PrescriptionItem;
import com.brytech.prescription_service.repository.PrescriptionItemRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("prescriptionItemJpa")
public class PrescriptionItemJpaDataAccessService implements PrescriptonItemDao {

    private final PrescriptionItemRepository repository;


    public PrescriptionItemJpaDataAccessService(PrescriptionItemRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<PrescriptionItem> saveAll(List<PrescriptionItem> items) {
       if (items == null || items.isEmpty()) {
           throw new IllegalArgumentException("List of PrescriptionItems must no be null or empty");
       }
       return repository.saveAll(items);
    }

    @Override public Page<PrescriptionItem> findByPrescriptionId(Long prescriptionId, Pageable pageable) {
        if (prescriptionId == null) {
            throw new IllegalArgumentException("Prescription ID must not be null");
        }
        if (pageable == null) {
            throw new IllegalArgumentException("Pageable must not be null");
        }
        return repository.findByPrescriptionId(prescriptionId, pageable);
    }

    @Override
    public void deleteByPrescriptionId(Long prescriptionId) {
        repository.deleteByPrescriptionId(prescriptionId);
    }
}
