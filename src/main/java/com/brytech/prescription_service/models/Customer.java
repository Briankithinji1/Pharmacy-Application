package com.brytech.prescription_service.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    private UUID id;

    private String first_name;
    private String last_name;
    private String email;
}
