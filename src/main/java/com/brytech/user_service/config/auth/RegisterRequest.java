package com.brytech.user_service.config.auth;

import com.brytech.user_service.model.Role;
import com.brytech.user_service.model.User;

import java.util.Set;

public record RegisterRequest(
        String firstname,
        String lastname,
        String email,
        String password,
        Set<String> roles
) {

    public User toUser(Set<Role> resolvedRoles) {
        return User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .password(password)
                .roles(resolvedRoles)
                .build();
    }
}
