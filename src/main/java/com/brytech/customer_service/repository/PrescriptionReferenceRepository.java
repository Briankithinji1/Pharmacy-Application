package com.brytech.customer_service.repository;

import com.brytech.customer_service.model.PrescriptionReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrescriptionReferenceRepository extends JpaRepository<PrescriptionReference, UUID> {

    Page<PrescriptionReference> findByCustomerId(UUID customerId, Pageable pageable);
}
