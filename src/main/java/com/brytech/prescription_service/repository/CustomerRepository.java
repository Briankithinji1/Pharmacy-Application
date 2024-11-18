package com.brytech.prescription_service.repository;

import java.util.UUID;

import com.brytech.prescription_service.models.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    
}
