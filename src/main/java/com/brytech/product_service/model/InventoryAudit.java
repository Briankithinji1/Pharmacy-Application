package com.brytech.product_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InventoryAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int changeQuantity;

    @Column(nullable = false)
    private String reason; // e.g., "OrderCreated", "OrderCancelled", "StockAdjustment"

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
