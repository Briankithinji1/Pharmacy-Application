package com.brytech.customer_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderReference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID order_id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String order_status; // E.g., "Pending", "Delivered", "Cancelled"
    private Instant order_date;
    
}
