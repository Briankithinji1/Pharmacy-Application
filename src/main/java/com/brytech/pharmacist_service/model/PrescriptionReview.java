package com.brytech.pharmacist_service.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.brytech.pharmacist_service.enumeration.ReviewStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Instant reviewedAt;
}
