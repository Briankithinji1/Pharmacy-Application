package main.java.com.example.Pharmacy.Application.user.dto;

public record PharmacistDTO(
        Long userId,
        String firstname,
        String lastname,
        String email,
        String password,
        String phoneNumber,
        String address,
        String qualification
) {}
