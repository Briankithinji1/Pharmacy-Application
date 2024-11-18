package com.brytech.customer_service.service;

import com.brytech.customer_service.dao.CustomerDAO;
import com.brytech.customer_service.dao.CustomerPreferenceDAO;
import com.brytech.customer_service.dto.CustomerDTO;
import com.brytech.customer_service.dto.CustomerPreferenceDTO;
import com.brytech.customer_service.dto.CustomerSummary;
import com.brytech.customer_service.exceptions.ResourceNotFoundException;
import com.brytech.customer_service.model.Customer;
import com.brytech.customer_service.model.CustomerPreference;
import com.brytech.customer_service.repository.CustomerPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerPreferenceService {

    private final CustomerPreferenceDAO preferenceDAO;
    private final CustomerDAO customerDAO;
    private final CustomerPreferenceRepository repository;
    private final ModelMapper mapper;

    public CustomerPreferenceDTO getCustomerPreferenceById(UUID id) {
        return preferenceDAO.getCustomerPreferenceById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Preference with ID [%s] not found".formatted(id)
                ));
    }

    public Page<CustomerPreferenceDTO> getPreferenceByCustomerId(UUID id, Pageable pageable) {
        return preferenceDAO.getPreferencesByCustomerId(id, pageable)
                .map(this::convertToDto);

    }

    public CustomerPreferenceDTO createCustomerPreference(CustomerPreferenceDTO preferenceDTO) {
        CustomerPreference preference = mapper.map(preferenceDTO, CustomerPreference.class);

        CustomerPreference savedPreference = preferenceDAO.createCustomerPreference(preference);

        return convertToDto(savedPreference);
    }

    public CustomerPreferenceDTO updateCustomerPreference(UUID id ,CustomerPreferenceDTO preferenceDTO) {
        CustomerPreference existingPreference = preferenceDAO.getCustomerPreferenceById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer Preference with ID [%s] not found".formatted(id)
                ));

        CustomerSummary customerSummary = customerDAO.getCustomerSummaryById(preferenceDTO.customer().customer_id())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Customer with ID [%s] not found".formatted(preferenceDTO.customer().customer_id())
                        ));

        existingPreference.setCustomer(new Customer(customerSummary.customer_id()));
        existingPreference.setPreference_type(preferenceDTO.preference_type());
        existingPreference.setValue(preferenceDTO.value());

        CustomerPreference updatedPreferences = preferenceDAO.updateCustomerPreference(existingPreference);

        return convertToDto(updatedPreferences);
    }

    public void deleteCustomerPreference(UUID id) {
        try {
            preferenceDAO.deleteCustomerPreference(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Customer with ID [%s] not found".formatted(id)
            );
        }
    }

    private CustomerPreferenceDTO convertToDto(CustomerPreference customerPreference) {
        return mapper.map(customerPreference, CustomerPreferenceDTO.class);
    }
}
