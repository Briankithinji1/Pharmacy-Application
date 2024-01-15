package com.example.Pharmacy.Application.config.auth;

import com.example.Pharmacy.Application.config.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    public LogoutService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return;

        jwtToken = authHeader.substring(7);
        var storeToken = tokenRepository.findByToken(jwtToken)
                .orElse(null);

        if (storeToken != null) {
            storeToken.setExpired(true);
            storeToken.setRevoked(true);
            tokenRepository.save(storeToken);
            SecurityContextHolder.clearContext();
        }
    }
}
