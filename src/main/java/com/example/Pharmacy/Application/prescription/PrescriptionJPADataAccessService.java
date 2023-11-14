package com.example.Pharmacy.Application.prescription;

import com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("prescriptionJPA")
public class PrescriptionJPADataAccessService implements PrescriptionDao {

    private final PrescriptionRepository repository;

    public PrescriptionJPADataAccessService(PrescriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Prescription> selectAllPrescriptions() {
        Page<Prescription> page = repository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<Prescription> selectPrescriptionById(Long prescriptionId) {
        return repository.findById(prescriptionId);
    }

    @Override
    public List<Prescription> selectAllPrescriptionsByUserId(Long userId) {
        return repository.findAllByCustomerUserId(userId);
    }

    @Override
    public List<Prescription> selectAllPrescriptionsByProductId(Long productId) {
        return repository.findByMedicineProductId(productId);
    }

    @Override
    public void insertPrescription(Prescription prescription) {
        repository.save(prescription);
    }

    @Override
    public void updatePrescription(Prescription prescription) {
        repository.save(prescription);
    }

    @Override
    public void deletePrescription(Long prescriptionId) {
        repository.deleteById(prescriptionId);
    }

    @Override
    public boolean isPrescriptionExistById(Long prescriptionId) {
        return repository.existsByPrescriptionId(prescriptionId);
    }

    @Override
    public boolean isPrescriptionExistByUserId(Long userId) {
        return repository.existsByCustomerUserId(userId);
    }

    @Override
    public boolean isPrescriptionExistByProductId(Long productId) {
        return repository.existsByMedicineProductId(productId);
    }

    @Override
    public void updatePrescriptionPrescriptionFileId(String prescriptionFileId, Long prescriptionId) {
        repository.updatePrescriptionFileId(prescriptionFileId, prescriptionId);
    }
}
