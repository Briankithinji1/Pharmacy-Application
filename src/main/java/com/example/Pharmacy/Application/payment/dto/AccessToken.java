package main.java.com.example.Pharmacy.Application.payment.dto;

public record AccessToken(
        String access_token,
        long expires_in
) {
}
