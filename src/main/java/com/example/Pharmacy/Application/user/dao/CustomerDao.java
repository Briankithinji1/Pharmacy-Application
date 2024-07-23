package main.java.com.example.Pharmacy.Application.user.dao;

import main.java.com.example.Pharmacy.Application.cart.enums.CartStatus;
import main.java.com.example.Pharmacy.Application.order.enums.OrderStatus;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import main.java.com.example.Pharmacy.Application.user.model.Pharmacist;
import main.java.com.example.Pharmacy.Application.user.model.Veterinarian;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerByUserId(Long userId);
    Optional<Customer> getCustomerByEmail(String email);
    Optional<Customer> getByAssignedPharmacist(Pharmacist pharmacist);
    Optional<Customer> getByAssignedVet(Veterinarian veterinarian);
    Optional<Customer> getByOrderList_Status(OrderStatus status);
    Optional<Customer> getByCartList_Status(CartStatus status);
    void insertCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(Long userId);
    boolean isCustomerExistsById(Long userId);
    boolean isCustomerExistsByEmail(String email);
    boolean isExistsByOrderList_Status(OrderStatus status);
    boolean isExistsByCartList_Status(CartStatus status);
}
