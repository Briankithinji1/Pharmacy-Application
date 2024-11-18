package com.brytech.customer_service.dao;

import com.brytech.customer_service.model.CustomerPreference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerPreferenceDAO {

    CustomerPreference createCustomerPreference(CustomerPreference preference);
    Optional<CustomerPreference> getCustomerPreferenceById(UUID preferenceId);
    Page<CustomerPreference> getPreferencesByCustomerId(UUID customerId, Pageable pageable);
    CustomerPreference updateCustomerPreference(CustomerPreference preference);
    void deleteCustomerPreference(UUID preferenceId);
}
