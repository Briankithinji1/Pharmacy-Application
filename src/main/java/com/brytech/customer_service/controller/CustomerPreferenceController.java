package com.brytech.customer_service.controller;

import com.brytech.customer_service.dto.CustomerPreferenceDTO;
import com.brytech.customer_service.service.CustomerPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/preferences")
@RequiredArgsConstructor
public class CustomerPreferenceController {

    private static final Logger getLog = LoggerFactory.getLogger(CustomerPreferenceController.class);

    private final CustomerPreferenceService preferenceService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerPreferenceDTO> getCustomerPreferenceById(
            @PathVariable("id")UUID id
    ) {
        CustomerPreferenceDTO preference = preferenceService.getCustomerPreferenceById(id);
        return ResponseEntity.ok(preference);
    }

    @GetMapping("/{cus_id}")
    public ResponseEntity<Page<CustomerPreferenceDTO>> getPreferenceByCustomerId(
            @PathVariable("cus_id")UUID id,
            Pageable pageable
    ) {
        Page<CustomerPreferenceDTO> preferences = preferenceService.getPreferenceByCustomerId(id, pageable);
        return ResponseEntity.ok(preferences);
    }

    @PostMapping("/preference")
    public ResponseEntity<CustomerPreferenceDTO> createCustomerPreference(
            @RequestBody CustomerPreferenceDTO preferenceDTO
    ) {
        CustomerPreferenceDTO createdPreference = preferenceService.createCustomerPreference(preferenceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPreference);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerPreferenceDTO> updateCustomerPreference(
            @PathVariable("id") UUID id,
            @Valid @RequestBody CustomerPreferenceDTO preferenceDTO
    ) {
        CustomerPreferenceDTO updatedPreference = preferenceService.updateCustomerPreference(id, preferenceDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPreference);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerPreference(
            @PathVariable("id") UUID id
    ) {
        preferenceService.deleteCustomerPreference(id);
    }
}
