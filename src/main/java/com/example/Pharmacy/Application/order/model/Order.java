package com.example.Pharmacy.Application.order.model;

import com.example.Pharmacy.Application.order.enums.OrderStatus;
import com.example.Pharmacy.Application.user.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
//    private String orderName;
//    private String description;
    private OrderStatus status;
//    private Double productPrice;
    private Integer quantity;
    private Double totalPrice;
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    // TODO: relationship with user
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
