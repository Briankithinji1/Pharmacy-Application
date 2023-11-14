package com.example.Pharmacy.Application.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;
    private String supplierName;
    private String email;
    private String address;
    private String phoneNumber;
    private String location;
    private String productType;
    private String productDescription;
    private String productQuantity;
    private String productPrice;
    private String productDiscount;
    private String invoice;

    //Add and link with orders table
}
