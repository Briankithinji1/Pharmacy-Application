package com.example.Pharmacy.Application.user.model;

import com.example.Pharmacy.Application.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pharmacist extends User {

    private String phoneNumber;
    private String address;
    private String qualification;

    @OneToMany(mappedBy = "pharmacist")
    private List<Product> products = new ArrayList<>();

    @ManyToMany(mappedBy = "assignedPharmacist")
    private List<Customer> customers;
}
