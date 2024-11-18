package com.brytech.customer_service.repository;

import com.brytech.customer_service.dto.CustomerSummary;
import com.brytech.customer_service.model.CustomerPreference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerPreferenceRepository extends JpaRepository<CustomerPreference, UUID> {

    List<CustomerPreference> findByCustomerId(UUID customerId);

    Page<CustomerPreference> findByCustomerId(UUID customerId, Pageable pageable);

    @Query("SELECT cp FROM CustomerPreference cp JOIN FETCH cp.customer c WHERE cp.preference_id = :id")
    Optional<CustomerPreference> getCustomerPreferenceById(@Param("id") UUID id);

    @Query("SELECT c.customer_id AS customer_id, c.first_name AS first_name, c.last_name AS last_name FROM Customer c WHERE c.customer_id = :id")
    Optional<CustomerSummary> getCustomerSummaryById(UUID id);

}
