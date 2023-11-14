package com.example.Pharmacy.Application.user.dao;

import com.example.Pharmacy.Application.user.model.Pharmacist;

import java.util.List;
import java.util.Optional;

public interface PharmacistDao {

    List<Pharmacist> getAllPharmacists();
    Optional<Pharmacist> getPharmacistsByUserId(Long userId);
    Optional<Pharmacist> getPharmacistsByEmail(String email);
    void insertPharmacist(Pharmacist pharmacist);
    void updatePharmacist(Pharmacist pharmacist);
    void deletePharmacist(Long userId);
    boolean isPharmacistExistsById(Long userId);
    boolean isPharmacistExistsByEmail(String email);
}
