package com.brytech.customer_service.model;

import com.brytech.customer_service.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String first_name;
    private String last_name;

    @Email
    private String email;
    private String phone_number;
    private String address;

    @Enumerated(EnumType.STRING)
    private AccountStatus account_status;

    public Customer(UUID customer_id) {
        this.id = customer_id;
    }

//    // Relationships
//    @ElementCollection
//    private Set<UUID> prescriptions = new HashSet<>();
//
//    @ElementCollection
//    private Set<UUID> orders = new HashSet<>();
//
//    @ElementCollection
//    private Set<UUID> payments = new HashSet<>();
}
