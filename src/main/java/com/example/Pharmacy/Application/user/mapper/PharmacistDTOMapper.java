package com.example.Pharmacy.Application.user.mapper;

import com.example.Pharmacy.Application.user.dto.PharmacistDTO;
import com.example.Pharmacy.Application.user.model.Pharmacist;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PharmacistDTOMapper implements Function<Pharmacist, PharmacistDTO> {

        @Override
        public PharmacistDTO apply(Pharmacist pharmacist) {
            return new PharmacistDTO(
                    pharmacist.getUserId(),
                    pharmacist.getFirstname(),
                    pharmacist.getLastname(),
                    pharmacist.getEmail(),
                    pharmacist.getPassword(),
                    pharmacist.getPhoneNumber(),
                    pharmacist.getAddress(),
                    pharmacist.getQualification()
            );
        }
}
