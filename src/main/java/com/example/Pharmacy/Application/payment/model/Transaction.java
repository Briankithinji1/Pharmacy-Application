package com.example.Pharmacy.Application.payment.model;

import com.example.Pharmacy.Application.order.model.Order;
import com.example.Pharmacy.Application.payment.enums.PaymentType;
import com.example.Pharmacy.Application.payment.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @Column(name = "transactionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private PaymentType paymentType;
    private TransactionStatus status;
    private String details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}