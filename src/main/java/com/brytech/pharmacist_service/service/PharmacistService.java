package com.brytech.pharmacist_service.service;

import java.time.Instant;
import java.util.List;

import jakarta.transaction.Transactional;

import com.brytech.pharmacist_service.dao.PharmacistDao;
import com.brytech.pharmacist_service.dto.InventoryDto;
import com.brytech.pharmacist_service.dto.PharmacistDto;
import com.brytech.pharmacist_service.event.LowStockAlertEvent;
import com.brytech.pharmacist_service.event.PharmacistEventPublisher;
import com.brytech.pharmacist_service.event.StockDispensedEvent;
import com.brytech.pharmacist_service.exception.RequestValidationException;
import com.brytech.pharmacist_service.exception.ResourceNotFoundException;
import com.brytech.pharmacist_service.model.Pharmacist;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacistService {

    private static final Logger logger = LoggerFactory.getLogger(PharmacistService.class);

    private final PharmacistDao pharmacistDao;
    private final ModelMapper mapper;
    private final InventoryClient inventoryClient;
    private final PharmacistEventPublisher eventPublisher;

    @Transactional
    public PharmacistDto updatePharmacist(Long pharmacistId, PharmacistDto pharmacistDto) {

        Pharmacist pharmacist = pharmacistDao.findById(pharmacistId)
            .orElseThrow(() -> new ResourceNotFoundException(
                         "Pharmacist with ID [%s] not found".formatted(pharmacistId)
            ));

        pharmacist.setName(pharmacistDto.name());
        pharmacist.setEmail(pharmacistDto.email());
        pharmacist.setPhoneNumber(pharmacistDto.phoneNumber());

        Pharmacist savedPharmacist = pharmacistDao.save(pharmacist);

        return convertToDto(savedPharmacist);
    }

    public PharmacistDto findByPharmacistId(Long id) {
        return pharmacistDao.findById(id)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException(
                         "Pharmacist with ID [%s] not found".formatted(id)
            ));
    }
 
    public PharmacistDto findByEmailOrPhoneNumber(String email, String phoneNumber) {
        if ((email == null || email.isBlank()) && (phoneNumber == null || phoneNumber.isBlank())) {
        throw new RequestValidationException("Both email and phone number cannot be null or empty.");
        }

        return pharmacistDao.findByEmailOrPhoneNumber(email, phoneNumber)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Pharmacist not found with the given email or phone number"
            ));
    }
 
    public List<PharmacistDto> findAllPharmacist() {
        return pharmacistDao.findAll()
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public List<PharmacistDto> findPharmacistByBranchId(Long branchId) {
        if (branchId == null) {
            throw new RequestValidationException("Branch ID cannot be null");
        }

        return pharmacistDao.findByBranchId(branchId)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public void deleteById(Long id) {
        try {
            pharmacistDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Pharmacist with ID [%s] does not exist".formatted(id)
            );
        }
    }

    public void delete(Pharmacist pharmacist) {
        if (pharmacist == null) {
            throw new RequestValidationException("Pharmacist cannot be null");
        }

        pharmacistDao.delete(pharmacist);
    }

    private PharmacistDto convertToDto(Pharmacist pharmacist) {
        return mapper.map(pharmacist, PharmacistDto.class);
    }


    // To dispense stock
    @Transactional
    public void dispenseStock(Long productId, int quantityDispensed) {
        InventoryDto inventory = inventoryClient.getInventoryByProductId(productId);

        if (inventory.availableStock() < quantityDispensed) {
            throw new RequestValidationException(
                    "Insufficient stock for product ID [%s]".formatted(productId)
            );
        }

        int remainingStock = inventory.availableStock() - quantityDispensed;

        // Update inventory or perform any domain-specific logic here
        logger.info("Dispensed {} units of product ID [{}]. Remaining stock: {}", quantityDispensed, productId, remainingStock);

        // Create events
        StockDispensedEvent dispensedEvent = new StockDispensedEvent(
                productId, 
                quantityDispensed, 
                remainingStock, 
                Instant.now()
        );

        LowStockAlertEvent alertEvent = null;
        if (remainingStock < inventory.lowStockThreshold()) {
            alertEvent = new LowStockAlertEvent(
                    productId, 
                    inventory.productName(), 
                    remainingStock, 
                    inventory.lowStockThreshold()
            );
        }

        eventPublisher.publishStockDispensedEvent(dispensedEvent);
        if (alertEvent != null) {
            eventPublisher.publishLowStockAlertEvent(alertEvent);
        }
    }
}
