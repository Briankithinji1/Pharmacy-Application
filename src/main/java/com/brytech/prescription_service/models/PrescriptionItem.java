package com.brytech.prescription_service.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PrescriptionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;
    private String dosage; // e.g., "500mg"
    private String frequency; // e.g., "3 times a day"
    private String instructions; // e.g., "Take after meals"
    private int quantity; // e.g., 30 pills

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}
