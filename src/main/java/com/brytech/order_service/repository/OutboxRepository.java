package com.brytech.order_service.repository;

import com.brytech.order_service.model.Outbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    
}
