package com.brytech.cart_service.repository;

import com.brytech.cart_service.model.Outbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    
}
