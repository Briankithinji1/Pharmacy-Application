package com.brytech.customer_service.repository;

import com.brytech.customer_service.model.OrderReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OrderReferenceRepository extends JpaRepository<OrderReference, UUID> {

    List<OrderReference> findByCustomerId(UUID customerId);

    Page<OrderReference> findByCustomerId(UUID customerId, Pageable pageable);

    List<OrderReference> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    Page<OrderReference> findByOrderDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

}
