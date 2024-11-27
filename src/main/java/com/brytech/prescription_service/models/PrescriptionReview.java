package com.brytech.prescription_service.models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import com.brytech.prescription_service.enums.ReviewStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class PrescriptionReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ReviewStatus reviewStatus;
    private String comments;

    @Column(name = "reviewed_at", nullable = false, updatable = false)
    private Instant reviewedAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne
    private PrescriptionUpload prescriptionUpload;

    @ManyToOne(optional = false)
    private final Pharmacist reviewer; //Immutable

    private boolean deleted = false;

    public PrescriptionReview(Pharmacist reviewer, PrescriptionUpload prescriptionUpload) {
        this.reviewer = reviewer;
        this.prescriptionUpload = prescriptionUpload;
    }

    @PrePersist
    protected void onCreate() {
        this.reviewedAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
