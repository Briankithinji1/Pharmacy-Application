package main.java.com.example.Pharmacy.Application.user.dto;

public record FinanceManagerDTO(
        Long userId,
        String firstname,
        String lastname,
        String email,
        String password,
        String phoneNumber,
        String address
) {
}
