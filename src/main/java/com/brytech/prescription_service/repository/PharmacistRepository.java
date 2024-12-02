package com.brytech.prescription_service.repository;

import com.brytech.prescription_service.models.Pharmacist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

}

