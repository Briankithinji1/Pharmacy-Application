package com.brytech.pharmacist_service.repository;

import java.util.List;
import java.util.Optional;

import com.brytech.pharmacist_service.model.Pharmacist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

    List<Pharmacist> findByBranchId(Long branchId);
    Optional<Pharmacist> findByEmail(String Email);
    Optional<Pharmacist> findByPhoneNumber(String phoneNumber);
}
