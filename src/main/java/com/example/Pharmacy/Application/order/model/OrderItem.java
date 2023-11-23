package com.example.Pharmacy.Application.order.model;

import com.example.Pharmacy.Application.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private @NotNull int quantity;
    private @NotNull double price;
    private Date createdDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "orderId")
    private Order order;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;
}
