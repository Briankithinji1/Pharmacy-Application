package com.brytech.product_service.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.brytech.product_service.enumeration.EventType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long aggregateId;
    private String aggregateType;
    private EventType eventType;
    private String payload;
    private Instant createdAt;
    private Instant sentAt;
    private Boolean processed = false;
    private Instant processedAt;
    private String status; // "Pending", "Sent", "Failed"
}
