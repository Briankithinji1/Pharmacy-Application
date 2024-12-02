package com.brytech.prescription_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.prescription_service.models.PrescriptionItem;
import com.brytech.prescription_service.repository.PrescriptionItemRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("prescriptionItemJpa")
public class PrescriptionItemJpaDataAccessService implements PrescriptionItemDao {

    private final PrescriptionItemRepository repository;

    public PrescriptionItemJpaDataAccessService(PrescriptionItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public PrescriptionItem saveItem(PrescriptionItem item) {
        return repository.save(item);
    }

    @Override
    public List<PrescriptionItem> saveAll(List<PrescriptionItem> items) {
       return repository.saveAll(items);
    }

    @Override
    public Optional<PrescriptionItem> findById(Long id) {
        return repository.findById(id);
    }

    @Override public Page<PrescriptionItem> findByPrescriptionId(Long prescriptionId, Pageable pageable) {
        return repository.findByPrescriptionId(prescriptionId, pageable);
    }

    @Override
    public List<PrescriptionItem> findByPrescriptionId(Long prescriptionId) {
        return repository.findByPrescriptionId(prescriptionId);
    }

    @Override
    public List<PrescriptionItem> findByNameAndPrescriptionId(String name, Long prescriptionId) {
        return repository.findByMedicineNameAndPrescriptionId(name, prescriptionId);
    }

    @Override
    public void deleteById(Long itemId) {
        repository.deleteById(itemId);
    }

    @Override
    public void deleteByPrescriptionId(Long prescriptionId) {
        repository.deleteByPrescriptionId(prescriptionId);
    }

    @Override
    public boolean existsByNameAndPrescriptionId(String name, Long prescriptionId) {
        return repository.existsByMedicineNameAndPrescriptionId(name, prescriptionId);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public long countByPrescriptionId(Long prescriptionId) {
        return repository.countByPrescriptionId(prescriptionId);
    }
}
