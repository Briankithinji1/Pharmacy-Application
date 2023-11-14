package com.example.Pharmacy.Application.user.service;

import com.example.Pharmacy.Application.exception.DuplicateResourceException;
import com.example.Pharmacy.Application.exception.RequestValidationException;
import com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import com.example.Pharmacy.Application.user.dao.PharmacistDao;
import com.example.Pharmacy.Application.user.dto.PharmacistDTO;
import com.example.Pharmacy.Application.user.mapper.PharmacistDTOMapper;
import com.example.Pharmacy.Application.user.model.Pharmacist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PharmacistService {

    private final PharmacistDTOMapper pharmacistDTOMapper;
    private final PharmacistDao pharmacistDao;

    public PharmacistService(PharmacistDTOMapper pharmacistDTOMapper, PharmacistDao pharmacistDao) {
        this.pharmacistDTOMapper = pharmacistDTOMapper;
        this.pharmacistDao = pharmacistDao;
    }

    public List<PharmacistDTO> getAllPharmacists() {
        return pharmacistDao.getAllPharmacists()
                .stream()
                .map(pharmacistDTOMapper)
                .toList();
    }

    public PharmacistDTO getPharmacistByUserId(Long userId) {
        return pharmacistDao.getPharmacistsByUserId(userId)
                .map(pharmacistDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pharmacist with Id [%s] not found".formatted(userId)
                ));
    }

    public Optional<PharmacistDTO> getPharmacistByEmail(String email) {
        return Optional.ofNullable(pharmacistDao.getPharmacistsByEmail(email)
                .map(pharmacistDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"
                )));
    }

    public void addPharmacist(Pharmacist pharmacist) {
        if (pharmacistDao.isPharmacistExistsByEmail(pharmacist.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already taken"
            );
        }
        pharmacistDao.insertPharmacist(pharmacist);
    }

    public void deletePharmacist(Long userId) {
        if (!pharmacistDao.isPharmacistExistsById(userId)) {
            throw new ResourceNotFoundException(
                    "User with Id [%s] not found".formatted(userId)
            );
        }
        pharmacistDao.deletePharmacist(userId);
    }

    public void updatePharmacist(Long userId, Pharmacist pharmacist) {
        Pharmacist pharmacist1 = pharmacistDao.getPharmacistsByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with Id [%s] not found".formatted(userId)
                ));

        boolean changes = false;

        if (pharmacist.getFirstname() != null && !pharmacist.getFirstname().equals(pharmacist1.getFirstname())) {
            pharmacist1.setFirstname(pharmacist.getFirstname());
            changes = true;
        }

        if (pharmacist.getLastname() != null && !pharmacist.getLastname().equals(pharmacist1.getLastname())) {
            pharmacist1.setLastname(pharmacist.getLastname());
            changes = true;
        }

        if (pharmacist.getEmail() != null && !pharmacist.getEmail().equals(pharmacist1.getEmail())) {
            if (pharmacistDao.isPharmacistExistsByEmail(pharmacist.getEmail())) {
                throw new DuplicateResourceException(
                        "Email already taken"
                );
            }
            pharmacist1.setEmail(pharmacist.getEmail());
            changes = true;
        }

        if (pharmacist.getGender() != null && !pharmacist.getGender().equals(pharmacist1.getGender())) {
            pharmacist1.setGender(pharmacist.getGender());
            changes = true;
        }

        if (pharmacist.getPhoneNumber() != null && !pharmacist.getPhoneNumber().equals(pharmacist1.getPhoneNumber())) {
            pharmacist1.setPhoneNumber(pharmacist.getPhoneNumber());
            changes = true;
        }

        if (pharmacist.getAddress() != null && !pharmacist.getAddress().equals(pharmacist1.getAddress())) {
            pharmacist1.setAddress(pharmacist.getAddress());
            changes = true;
        }

        if (pharmacist.getQualification() != null && !pharmacist.getQualification().equals(pharmacist1.getQualification())) {
            pharmacist1.setQualification(pharmacist.getQualification());
            changes = true;
        }

        if (pharmacist.getProducts() != null && !pharmacist.getProducts().equals(pharmacist1.getProducts())) {
            pharmacist1.setProducts(pharmacist.getProducts());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException(
                    "No changes made"
            );
        }

        pharmacistDao.updatePharmacist(pharmacist);
    }
}
