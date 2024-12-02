package com.brytech.prescription_service.models;

import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pharmacist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String licenseNumber;
    private String speciality;

    @OneToMany(mappedBy = "pharmacist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions;
}
