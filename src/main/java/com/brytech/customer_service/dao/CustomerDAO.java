package com.brytech.customer_service.dao;

import com.brytech.customer_service.dto.CustomerSummary;
import com.brytech.customer_service.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CustomerDAO {

    Optional<Customer> getCustomerById(UUID customerId);
    Optional<Customer> getCustomerByEmail(String email);
    Optional<CustomerSummary> getCustomerSummaryById(UUID id);
    Customer updateCustomer(Customer customer);
    void deleteCustomer(UUID customerId);
    Page<Customer> getAllCustomers(Pageable pageable);
    Page<Customer> getCustomersByStatus(String status, Pageable pageable);
}
