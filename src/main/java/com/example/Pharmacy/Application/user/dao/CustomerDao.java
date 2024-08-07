package main.java.com.example.Pharmacy.Application.user.dao;

import main.java.com.example.Pharmacy.Application.cart.enums.CartStatus;
import main.java.com.example.Pharmacy.Application.order.enums.OrderStatus;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import main.java.com.example.Pharmacy.Application.user.model.Pharmacist;
import main.java.com.example.Pharmacy.Application.user.model.Veterinarian;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    // Basic CRUD operations
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerByUserId(Long userId);
    Optional<Customer> getCustomerByEmail(String email);

    // Assignments and Relationships
    List<Customer> getByAssignedPharmacist(Pharmacist pharmacist);
    List<Customer> getByAssignedVet(Veterinarian veterinarian);

    // Order and Cart Status
    List<Customer> getByOrderList_Status(OrderStatus status);
    List<Customer> getByCartList_Status(CartStatus status);

    // Inset, Update, Delete
    void insertCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(Long userId);

    // Existence checks
    boolean isCustomerExistsById(Long userId);
    boolean isCustomerExistsByEmail(String email);
    boolean isExistsByOrderList_Status(OrderStatus status);
    boolean isExistsByCartList_Status(CartStatus status);
}
