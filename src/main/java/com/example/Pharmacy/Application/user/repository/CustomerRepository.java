package main.java.com.example.Pharmacy.Application.user.repository;

import main.java.com.example.Pharmacy.Application.cart.enums.CartStatus;
import main.java.com.example.Pharmacy.Application.order.enums.OrderStatus;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import main.java.com.example.Pharmacy.Application.user.model.Pharmacist;
import main.java.com.example.Pharmacy.Application.user.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByUserId(Long userId);
    boolean existsCustomerByEmail(String email);

    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findByAssignedPharmacist(Pharmacist pharmacist);
    Optional<Customer> findByAssignedVet(Veterinarian veterinarian);
    Optional<Customer> findByOrderList_Status(OrderStatus status);
    Optional<Customer> findByCartList_Status(CartStatus status);
    boolean existsByOrderList_Status(OrderStatus status);
    boolean existsByCartList_Status(CartStatus status);
}
