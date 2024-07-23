package main.java.com.example.Pharmacy.Application.payment.repository;

import main.java.com.example.Pharmacy.Application.order.model.Order;
import main.java.com.example.Pharmacy.Application.payment.enums.PaymentType;
import main.java.com.example.Pharmacy.Application.payment.model.Payment;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByCustomer(Customer customer);
    Optional<Payment> findByOrder(Order order);
    Optional<Payment> findByType(PaymentType type);

}
