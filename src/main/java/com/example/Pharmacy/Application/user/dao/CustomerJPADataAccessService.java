package main.java.com.example.Pharmacy.Application.user.dao;

import main.java.com.example.Pharmacy.Application.cart.enums.CartStatus;
import main.java.com.example.Pharmacy.Application.order.enums.OrderStatus;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import main.java.com.example.Pharmacy.Application.user.model.Pharmacist;
import main.java.com.example.Pharmacy.Application.user.model.Veterinarian;
import main.java.com.example.Pharmacy.Application.user.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("customerJPA")
public class CustomerJPADataAccessService implements CustomerDao {

    private final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        Page<Customer> page = customerRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<Customer> getCustomerByUserId(Long userId) {
        return customerRepository.findById(userId);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    public Optional<Customer> getByAssignedPharmacist(Pharmacist pharmacist) {
        return customerRepository.findByAssignedPharmacist(pharmacist);
    }

    @Override
    public Optional<Customer> getByAssignedVet(Veterinarian veterinarian) {
        return customerRepository.findByAssignedVet(veterinarian);
    }

    @Override
    public Optional<Customer> getByOrderList_Status(OrderStatus status) {
        return customerRepository.findByOrderList_Status(status);
    }

    @Override
    public Optional<Customer> getByCartList_Status(CartStatus status) {
        return customerRepository.findByCartList_Status(status);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long userId) {
        customerRepository.deleteById(userId);
    }

    @Override
    public boolean isCustomerExistsById(Long userId) {
        return customerRepository.existsCustomerByUserId(userId);
    }

    @Override
    public boolean isCustomerExistsByEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public boolean isExistsByOrderList_Status(OrderStatus status) {
        return customerRepository.existsByOrderList_Status(status);
    }

    @Override
    public boolean isExistsByCartList_Status(CartStatus status) {
        return customerRepository.existsByCartList_Status(status);
    }
}
