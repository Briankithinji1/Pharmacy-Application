package com.example.Pharmacy.Application.user.controller;

import com.example.Pharmacy.Application.user.dto.PharmacistDTO;
import com.example.Pharmacy.Application.user.model.Pharmacist;
import com.example.Pharmacy.Application.user.service.PharmacistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pharmacist")
public class PharmacistController {

    private final PharmacistService pharmacistService;

    @GetMapping("byUserId/{userId}")
    public PharmacistDTO getPharmacistByUserId(
            @PathVariable("userId") Long userId
    ) {
        return pharmacistService.getPharmacistByUserId(userId);
    }

    @GetMapping("allPharmacists")
    public List<PharmacistDTO> getAllPharmacists() {
        return pharmacistService.getAllPharmacists();
    }

    @GetMapping("{email}")
    public Optional<PharmacistDTO> getPharmacistByEmail(
            @PathVariable("email") String email
    ) {
        return pharmacistService.getPharmacistByEmail(email);
    }

    @PostMapping("addPharmacist")
    public ResponseEntity<?> addPharmacist(
            @RequestBody Pharmacist pharmacist
    ) {
        pharmacistService.addPharmacist(pharmacist);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{userId}/delete")
    public void deletePharmacist(
            @PathVariable("userId") Long userId
    ) {
        pharmacistService.deletePharmacist(userId);
    }

    @PutMapping("{userId}/update")
    public void updatePharmacist(
            @PathVariable("userId") Long userId,
            @RequestBody Pharmacist pharmacist
    ) {
        pharmacistService.updatePharmacist(userId, pharmacist);
    }
}
