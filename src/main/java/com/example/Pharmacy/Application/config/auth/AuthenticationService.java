package com.example.Pharmacy.Application.config.auth;

import com.example.Pharmacy.Application.config.jwt.JWTService;
import com.example.Pharmacy.Application.config.security.SecurityService;
import com.example.Pharmacy.Application.config.token.TokenService;
import com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import com.example.Pharmacy.Application.user.model.Role;
import com.example.Pharmacy.Application.user.model.User;
import com.example.Pharmacy.Application.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final SecurityService securityService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager, TokenService tokenService, SecurityService securityService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.securityService = securityService;
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var principal = User.builder()
                .firstname(registerRequest.firstname())
                .lastname(registerRequest.lastname())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(Role.USER)
                .build();
        userRepository.save(principal);
//        User user = registerRequest.toUser();
        String accessToken = jwtService.issueToken(principal.getEmail());
        String refreshToken = jwtService.issueRefreshToken(principal.getEmail());
        tokenService.saveUserToken(principal, accessToken);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.email(),
                            authRequest.password()
                    )
            );
        } catch (InternalAuthenticationServiceException e) {
            log.error("Error while authenticating user with request {}", authRequest);
            throw new BadCredentialsException("Invalid Credentials");
        }

        UserDetails userDetails = securityService.loadUserByUsername(authRequest.email());
        User user = (User) userDetails;

        var accessToken = jwtService.issueToken(user.getUsername());
        var refreshToken = jwtService.issueRefreshToken(user.getEmail());

        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, accessToken);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public void refreshToken(
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse
    ) throws IOException {

        final String authHeader = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return;

        refreshToken = authHeader.substring(7);
        email = jwtService.getSubject(refreshToken);

        if (email != null) {
            var userDetails = this.userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "User not found"
                    ));

            if (jwtService.isTokenValid(refreshToken, userDetails.getUsername())) {
                var accessToken = jwtService.issueToken(userDetails.getUsername());
                tokenService.revokeAllUserTokens(userDetails);
                tokenService.saveUserToken(userDetails, accessToken);

                new AuthenticationResponse(accessToken, refreshToken);
            } else {
                servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid refresh token");
            }
        } else {
            servletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Email not found");
        }
    }
}
