package com.brytech.prescription_service.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.brytech.prescription_service.enums.PrescriptionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PrescriptionUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;
    private Instant uploadDate;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Prescription linkedPrescription;

    @OneToMany(mappedBy = "prescriptionUpload", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionReview> reviews = new ArrayList<>();
}
