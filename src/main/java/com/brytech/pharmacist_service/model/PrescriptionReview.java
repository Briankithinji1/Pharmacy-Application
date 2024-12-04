package com.brytech.pharmacist_service.model;

import com.brytech.pharmacist_service.enumeration.ReviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PrescriptionReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pharmacist_id", nullable = false)
    private Pharmacist pharmacist;

    @Column(nullable = false)
    private Long prescriptionId;

    @Column(nullable = false)
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus reviewStatus; // APPROVED, REJECTED, PENDING

    @Column(nullable = false)
    private LocalDateTime reviewedAt;
}
