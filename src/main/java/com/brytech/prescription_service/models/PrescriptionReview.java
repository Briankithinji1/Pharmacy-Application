package com.brytech.prescription_service.models;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import com.brytech.prescription_service.enums.ReviewStatus;

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

    private ReviewStatus reviewStatus;
    private String comments;
    private Instant reviewedAt;

    @ManyToOne
    private PrescriptionUpload prescriptionUpload;

    @ManyToOne
    private Pharmacist reviewer;

    private boolean deleted = false;
}
