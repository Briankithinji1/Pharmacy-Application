package com.brytech.customer_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID aggregate_id; // E.g., Customer Id, Order Id
    private String aggregate_type; // E.g., "Customer", "Order"
    private String event_type; // E.g., "OrderPlaced", "PrescriptionUpdated"
    private String payload; // JSON
    private Instant created_at;
    private Instant sent_at;
    private Boolean processed = false; // To mark if processed by Camel
    private String status; // E.g., "Pending", "Sent", "Failed"
}
