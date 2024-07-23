package main.java.com.example.Pharmacy.Application.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.java.com.example.Pharmacy.Application.cart.model.Cart;
import main.java.com.example.Pharmacy.Application.category.Category;
import main.java.com.example.Pharmacy.Application.prescription.Prescription;
import main.java.com.example.Pharmacy.Application.user.model.Pharmacist;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

//    @ManyToMany(mappedBy = "products")
//    private Set<Order> orders = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Cart> carts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacistId", referencedColumnName = "userId")
    private Pharmacist pharmacist;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions = new ArrayList<>();
}
