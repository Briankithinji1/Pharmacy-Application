package com.brytech.pharmacist_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.pharmacist_service.model.Pharmacist;
import com.brytech.pharmacist_service.repository.PharmacistRepository;

import org.springframework.stereotype.Repository;


@Repository("pharmacistJpa")
public class PharmacistJpaDataAccessService implements PharmacistDao {

    private final PharmacistRepository pharmacistRepository;

    public PharmacistJpaDataAccessService(PharmacistRepository pharmacistRepository) {
        this.pharmacistRepository = pharmacistRepository;
    }

    @Override
    public Pharmacist save(Pharmacist pharmacist) {
        return pharmacistRepository.save(pharmacist);
    }

    @Override
    public Optional<Pharmacist> findById(Long id) {
        return pharmacistRepository.findById(id);
    }

    @Override
    public List<Pharmacist> findAll() {
        return pharmacistRepository.findAll();
    }

    @Override
    public List<Pharmacist> findByBranchId(Long branchId) {
        return pharmacistRepository.findByBranchId(branchId);
    }

    @Override
    public Optional<Pharmacist> findByEmail(String email) {
        return pharmacistRepository.findByEmail(email);
    }

    @Override
    public Optional<Pharmacist> findByPhoneNumber(String phoneNumber) {
        return pharmacistRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteById(Long id) {
        pharmacistRepository.deleteById(id);
    }

    @Override
    public void delete(Pharmacist pharmacist) {
        pharmacistRepository.delete(pharmacist);
    }

    
}
