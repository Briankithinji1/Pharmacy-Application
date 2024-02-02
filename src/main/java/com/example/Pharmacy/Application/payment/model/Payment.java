package com.example.Pharmacy.Application.payment.model;

import com.example.Pharmacy.Application.order.model.Order;
import com.example.Pharmacy.Application.payment.enums.PaymentStatus;
import com.example.Pharmacy.Application.payment.enums.PaymentType;
import com.example.Pharmacy.Application.user.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private BigDecimal amount;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
//    private Map<String, String> paymentMetadata = null;

//    @Column(name = "account_number")
//    private String BillRefNumber;

//    @Column(name = "business_short_code")
//    private int BusinessShortCode;

    @OneToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
