package com.brytech.prescription_service.repository;

import com.brytech.prescription_service.models.Outbox;

import org.springframework.data.jpa.repository.JpaRepository;


public interface OutboxRepository extends JpaRepository<Outbox, Long> {

}
