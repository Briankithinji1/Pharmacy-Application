package com.brytech.pharmacist_service.repository;

import com.brytech.pharmacist_service.model.Outbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    
}
