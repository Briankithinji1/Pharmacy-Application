package com.example.Pharmacy.Application.user.dao;

import com.example.Pharmacy.Application.user.model.Pharmacist;
import com.example.Pharmacy.Application.user.repository.PharmacistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("pharmacistJPA")
public class PharmacistJPADataAccessService implements PharmacistDao {

    private final PharmacistRepository pharmacistRepository;

    public PharmacistJPADataAccessService(PharmacistRepository pharmacistRepository) {
        this.pharmacistRepository = pharmacistRepository;
    }

    @Override
    public List<Pharmacist> getAllPharmacists() {
        Page<Pharmacist> page = pharmacistRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<Pharmacist> getPharmacistsByUserId(Long userId) {
        return pharmacistRepository.findById(userId);
    }

    @Override
    public Optional<Pharmacist> getPharmacistsByEmail(String email) {
        return pharmacistRepository.findPharmacistsByEmail(email);
    }

    @Override
    public void insertPharmacist(Pharmacist pharmacist) {
        pharmacistRepository.save(pharmacist);
    }

    @Override
    public void updatePharmacist(Pharmacist pharmacist) {
        pharmacistRepository.save(pharmacist);
    }

    @Override
    public void deletePharmacist(Long userId) {
        pharmacistRepository.deleteById(userId);
    }

    @Override
    public boolean isPharmacistExistsById(Long userId) {
        return pharmacistRepository.existsPharmacistByUserId(userId);
    }

    @Override
    public boolean isPharmacistExistsByEmail(String email) {
        return pharmacistRepository.existsPharmacistByEmail(email);
    }
}
