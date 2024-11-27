package com.brytech.prescription_service.models;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.brytech.prescription_service.enums.EventType;

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

    private Long aggragateId;
    private String aggregateType;
    private EventType eventType;
    private String payload;
    private Instant createdAt;
    private Instant sentAt;
    private Boolean processed = false;
    private Instant processedAt;
    private String status; // "Pending", "Sent", "Failed"
}
