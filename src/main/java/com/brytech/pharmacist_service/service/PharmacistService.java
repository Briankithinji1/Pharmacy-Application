package com.brytech.pharmacist_service.service;

import java.util.List;

import jakarta.transaction.Transactional;

import com.brytech.pharmacist_service.dao.PharmacistDao;
import com.brytech.pharmacist_service.dto.PharmacistDto;
import com.brytech.pharmacist_service.exception.RequestValidationException;
import com.brytech.pharmacist_service.exception.ResourceNotFoundException;
import com.brytech.pharmacist_service.model.Pharmacist;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PharmacistService {

    private final PharmacistDao pharmacistDao;
    private final ModelMapper mapper;

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

    public PharmacistDto findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new RequestValidationException("Email cannot be null or empty");
        }

        return pharmacistDao.findByEmail(email)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Pharmacist with Email [%s] not found".formatted(email)
            ));
    }

    public PharmacistDto findByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new RequestValidationException("Phone number cannot be null or empty");
        }

        return pharmacistDao.findByPhoneNumber(phoneNumber)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Pharmacist with contact [%s] not found".formatted(phoneNumber)
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
}
