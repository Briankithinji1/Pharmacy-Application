package com.brytech.customer_service.dao;

import com.brytech.customer_service.model.CustomerPreference;
import com.brytech.customer_service.repository.CustomerPreferenceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("customerReferenceJpa")
public class CustomerPreferenceJpaDataAccessService implements CustomerPreferenceDAO{

    private final CustomerPreferenceRepository repository;

    public CustomerPreferenceJpaDataAccessService(CustomerPreferenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerPreference createCustomerPreference(CustomerPreference preference) {
        return repository.save(preference);
    }

    @Override
    public Optional<CustomerPreference> getCustomerPreferenceById(UUID preferenceId) {
        return repository.findById(preferenceId);
    }

    @Override
    public Page<CustomerPreference> getPreferencesByCustomerId(UUID customerId, Pageable pageable) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }
        return repository.findByCustomerId(customerId, pageable);
    }

    @Override
    public CustomerPreference updateCustomerPreference(CustomerPreference preference) {
        return repository.save(preference);
    }

    @Override
    public void deleteCustomerPreference(UUID preferenceId) {
        repository.deleteById(preferenceId);
    }
}
