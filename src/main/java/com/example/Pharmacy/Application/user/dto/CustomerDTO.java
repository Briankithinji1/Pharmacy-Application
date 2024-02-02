package com.example.Pharmacy.Application.user.dto;

public record CustomerDTO(
        Long userId,
        String firstname,
        String lastname,
        String email,
//        String password,
        String phoneNumber,
        String address,
        String medicalHistory
) {
}
