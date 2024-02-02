package com.example.Pharmacy.Application.payment.repository;

import com.example.Pharmacy.Application.order.model.Order;
import com.example.Pharmacy.Application.payment.enums.PaymentType;
import com.example.Pharmacy.Application.payment.model.Payment;
import com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByCustomer(Customer customer);
    Optional<Payment> findByOrder(Order order);
    Optional<Payment> findByType(PaymentType type);

}
