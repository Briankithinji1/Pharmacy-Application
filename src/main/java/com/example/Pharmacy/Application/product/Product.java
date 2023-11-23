package com.example.Pharmacy.Application.product;

import com.example.Pharmacy.Application.cart.model.Cart;
import com.example.Pharmacy.Application.category.Category;
import com.example.Pharmacy.Application.order.model.Order;
import com.example.Pharmacy.Application.prescription.Prescription;
import com.example.Pharmacy.Application.user.model.Pharmacist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String description;
    private String imageUri;
    private Double price;
    private String quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;
    private boolean isAvailable;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;

    //relationship with category, pharmacy, order, cart, user, supplier

    @ManyToMany(mappedBy = "products")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Cart> carts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacistId", referencedColumnName = "userId")
    private Pharmacist pharmacist;

    @ManyToOne
    @JoinColumn(name = "prescription_is")
    private List<Prescription> prescriptions;
}
