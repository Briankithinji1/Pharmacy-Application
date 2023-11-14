package com.example.Pharmacy.Application.cart;

import com.example.Pharmacy.Application.product.Product;
import com.example.Pharmacy.Application.user.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    //private Long productId;
    //private Long userId;
    //private String productName;
    private String productDescription;
    private Double productPrice;
    private Integer quantity;
    private Double totalPrice;
    private CartStatus status;

    @ManyToMany
    @JoinTable(
            name = "carts_products",
            joinColumns = @JoinColumn(name = "cartId"),
            inverseJoinColumns = @JoinColumn(name = "productId")
    )
    private Set<Product> products = new HashSet<>();

    // TODO: relationship with user
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
