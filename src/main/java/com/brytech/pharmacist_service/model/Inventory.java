package com.brytech.pharmacist_service.model;

import com.brytech.pharmacist_service.dto.ProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private PharmacyBranch branch;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductDto product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer threshold;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
