package com.brytech.prescription_service.models;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import com.brytech.prescription_service.enums.PrescriptionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prescriptionNumber;
    private PrescriptionStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    private boolean uploadedByPatient;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "prescription")
    private List<PrescriptionItem> items;
    
}
