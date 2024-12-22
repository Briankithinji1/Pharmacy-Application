package com.brytech.product_service.repository;

import com.brytech.product_service.model.Outbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    
}
