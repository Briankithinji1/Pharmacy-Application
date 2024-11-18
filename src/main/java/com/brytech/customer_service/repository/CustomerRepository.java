package com.brytech.customer_service.repository;

import com.brytech.customer_service.dto.CustomerSummary;
import com.brytech.customer_service.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByEmail(String email);

    Page<Customer> findByStatus(String status, Pageable pageable);

    @Query("SELECT c.customer_id AS customer_id, c.first_name AS first_name, c.last_name AS last_name FROM Customer c WHERE c.customer_id = :id")
    Optional<CustomerSummary> getCustomerSummaryById(UUID id);
}
