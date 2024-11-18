package com.brytech.customer_service.dao;

import com.brytech.customer_service.dto.CustomerSummary;
import com.brytech.customer_service.model.Customer;
import com.brytech.customer_service.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository("customerJpa")
public class CustomerJpaDataAccessService implements CustomerDAO{

    private final CustomerRepository customerRepository;

    public CustomerJpaDataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Optional<CustomerSummary> getCustomerSummaryById(UUID id) {
        return customerRepository.getCustomerSummaryById(id);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Page<Customer> getCustomersByStatus(String status, Pageable pageable) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status must not be null or empty");
        }
        return customerRepository.findByStatus(status, pageable);
    }
}
