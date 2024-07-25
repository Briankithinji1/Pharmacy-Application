package main.java.com.example.Pharmacy.Application.config.auth;

import main.java.com.example.Pharmacy.Application.user.model.Role;
import main.java.com.example.Pharmacy.Application.user.model.User;

public record RegisterRequest(
        String firstname,
        String lastname,
        String email,
        String password,
        Role role
) {

    public User toUser() {
        return User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}
