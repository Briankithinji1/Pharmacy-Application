package com.example.Pharmacy.Application.order;

import com.example.Pharmacy.Application.product.Product;
import com.example.Pharmacy.Application.user.model.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderName;
    private String description;
    private OrderStatus status;
//    private Double productPrice;
    private Integer quantity;
    private Double totalPrice;
    private LocalDateTime orderDate;

    @ManyToMany
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "productId"),
            indexes = {
                    @Index(name = "idx_order_product_order_id", columnList = "orderId"),
                    @Index(name = "idx_order_product_product_id", columnList = "productId")
            }
    )
    private Set<Product> products = new HashSet<>();

    // TODO: relationship with user
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Method to calculate the total price based on products

    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        double total = 0.0;
        for (Product product: products) {
            total += product.getPrice() * quantity;
        }
        this.totalPrice = total;
    }
}
