package main.java.com.example.Pharmacy.Application.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import main.java.com.example.Pharmacy.Application.cart.model.Cart;
import main.java.com.example.Pharmacy.Application.order.model.Order;
import main.java.com.example.Pharmacy.Application.payment.model.Payment;
import main.java.com.example.Pharmacy.Application.prescription.Prescription;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends User {

    private String address;
    private String phoneNumber;
    private String medicalHistory;

    @OneToMany(mappedBy = "customer")
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Cart> cartList;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Payment> payment;

    @ManyToMany
    @JoinTable(
            name = "customer_pharmacist",
            joinColumns = @JoinColumn(name = "customerId"),
            inverseJoinColumns = @JoinColumn(name = "pharmacistId"),
            indexes = {
                    @Index(name = "idx_customer_pharmacist_customer_id", columnList = "customerId"),
                    @Index(name = "idx_customer_pharmacist_pharmacist_id", columnList = "pharmacistId")
            }
    )
    private List<Pharmacist> assignedPharmacist = new ArrayList<>();

    // ToDo: One To Many relationship with payments, delivery, feedback

    @ManyToMany
    @JoinTable(
            name = "customer_veterinarian",
            joinColumns = @JoinColumn(name = "customerId"),
            inverseJoinColumns = @JoinColumn(name = "veterinarianId"),
            indexes = {
                    @Index(name = "idx_customer_veterinarian_customer_id", columnList = "customerId"),
                    @Index(name = "idx_customer_veterinarian_veterinarianId_id", columnList = "veterinarianId")
            }
    )
    private List<Veterinarian> assignedVet = new ArrayList<>();
}
