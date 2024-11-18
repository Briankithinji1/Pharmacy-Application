package com.brytech.user_service.config.auth;

import com.brytech.user_service.config.email.EmailService;
import com.brytech.user_service.config.jwt.JWTService;
import com.brytech.user_service.config.password.ResetPasswordRequest;
import com.brytech.user_service.config.security.SecurityService;
import com.brytech.user_service.config.token.Token;
import com.brytech.user_service.config.token.TokenRepository;
import com.brytech.user_service.config.token.TokenService;
import com.brytech.user_service.enums.RoleType;
import com.brytech.user_service.exception.ResourceNotFoundException;
import com.brytech.user_service.model.Role;
import com.brytech.user_service.model.User;
import com.brytech.user_service.repository.RoleRepository;
import com.brytech.user_service.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final SecurityService securityService;
    private final EmailService emailService;
    private final MessageSource messageSource;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;

    private String frontendUrl;
    private String[] recipients;

    @Value("${mailing.frontend.activation-url}")
    private String activationUrl;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager, TokenService tokenService, SecurityService securityService, EmailService emailService, MessageSource messageSource, TokenRepository tokenRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.securityService = securityService;
        this.emailService = emailService;
        this.messageSource = messageSource;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
    }

    public void register(RegisterRequest request) throws MessagingException {
        // Map role names (strings) to actual Role entity
        Set<Role> roles = request.roles().stream()
                .map(roleName -> roleRepository.findByName(RoleType.valueOf(roleName))
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        var principal = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountLocked(false)
                .enabled(false)
                .roles(roles)
                .build();
        userRepository.save(principal);

        sendValidationEmail(principal);

//        String accessToken = jwtService.issueToken(principal.getEmail());
//        String refreshToken = jwtService.issueRefreshToken(principal.getEmail());
//        tokenService.saveUserToken(principal, accessToken);
//        new AuthenticationResponse(accessToken, refreshToken);
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

    public String resetPassword(String email, ResetPasswordRequest passwordRequest) {
        email = email.toLowerCase();
        User user = userRepository.findUserByEmail(email).get();
        String password = passwordRequest.password();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        String finalEmail = email;
        Map<String, Object> variables = new HashMap<String, Object>() {{
            put("loginLink", frontendUrl + "/auth/login?email=" + finalEmail);
            put("password", password);
        }};
        emailService.sendMessageUsingThymeleafTemplate(new String[]{email}, messageSource.getMessage("password_reset", null, getLocale(user)), variables, "reset-password.html");
        return "Password reset successfully";
    }

    private Locale getLocale(User user) {
        return Locale.getDefault();
    }

    @Transactional
    public AuthenticationResponse activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Token"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email address.");
        }

        var user = userRepository.findById(savedToken.getUser().getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

        String accessToken = jwtService.issueToken(user.getEmail());
        String refreshToken = jwtService.issueRefreshToken(user.getEmail());
        tokenService.saveUserToken(user, accessToken);

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode();
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        String[] to = { user.getEmail() };
        String subject = "Account Activation";
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("name", user.getFullName());
        templateModel.put("activationLink", activationUrl + "?token=" + newToken);

        String template = "activationEmailTemplate";

        emailService.sendMessageUsingThymeleafTemplate(to, subject, templateModel, template);
    }

    // Method to Generate Validation Code
    private String generateActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        // Generate a random activation code of the specified length
        for (int i = 0; i < 6; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
