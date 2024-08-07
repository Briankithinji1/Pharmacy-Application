package main.java.com.example.Pharmacy.Application.prescription;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.java.com.example.Pharmacy.Application.product.Product;
import main.java.com.example.Pharmacy.Application.user.model.Customer;

import java.time.Instant;

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

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;

    @Column(nullable = false, updatable = false)
    private Instant dateCreated;

    @Column(nullable = false)
    private Instant dateUpdated;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @PrePersist
    protected void onCreate() {
        dateCreated = Instant.now();
        dateUpdated = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateUpdated = Instant.now();
    }
}
