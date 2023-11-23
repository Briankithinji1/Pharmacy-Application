package com.example.Pharmacy.Application.prescription;

import com.example.Pharmacy.Application.product.Product;
import com.example.Pharmacy.Application.user.model.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long prescriptionId;
    private String dosage;
    private String instructionsOfUse;
    private String usageDuration;
    private String prescriptionFileId; // Uploading the prescription

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

//    @ManyToMany
//    @JoinTable(
//            name = "prescription_product",
//            joinColumns = @JoinColumn(name = "prescriptionId"),
//            inverseJoinColumns = @JoinColumn(name = "productId"),
//            indexes = {
//                    @Index(name = "idx_prescription_product_prescription_id", columnList = "prescriptionId"),
//                    @Index(name = "idx_prescription_product_product_id", columnList = "productId")
//            }
//    )
    @OneToMany(mappedBy = "prescription")
    private List<Product> medicine;
}
