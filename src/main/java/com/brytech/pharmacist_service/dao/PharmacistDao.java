package com.brytech.pharmacist_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.pharmacist_service.model.Pharmacist;

public interface PharmacistDao {

    Pharmacist save(Pharmacist pharmacist);
    Optional<Pharmacist> findById(Long id);
    List<Pharmacist> findAll();
    List<Pharmacist> findByBranchId(Long branchId);
    Optional<Pharmacist> findByEmail(String email);
    Optional<Pharmacist> findByPhoneNumber(String phoneNumber);
    Optional<Pharmacist> findByEmailOrPhoneNumber(String email, String phoneNumber);
    void deleteById(Long id);
    void delete(Pharmacist pharmacist);
}
