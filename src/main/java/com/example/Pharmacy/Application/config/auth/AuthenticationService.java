package main.java.com.example.Pharmacy.Application.config.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import main.java.com.example.Pharmacy.Application.config.email.EmailService;
import main.java.com.example.Pharmacy.Application.config.jwt.JWTService;
import main.java.com.example.Pharmacy.Application.config.password.ResetPasswordRequest;
import main.java.com.example.Pharmacy.Application.config.security.SecurityService;
import main.java.com.example.Pharmacy.Application.config.token.TokenService;
import main.java.com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import main.java.com.example.Pharmacy.Application.user.model.Role;
import main.java.com.example.Pharmacy.Application.user.model.User;
import main.java.com.example.Pharmacy.Application.user.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    private String frontendUrl;
    private String[] recipients;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager, TokenService tokenService, SecurityService securityService, EmailService emailService, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.securityService = securityService;
        this.emailService = emailService;
        this.messageSource = messageSource;
    }

    public void register(RegisterRequest registerRequest) {
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
        new AuthenticationResponse(accessToken, refreshToken);
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
        emailService.sendMessageUsingThymeleafTemplate(new String[]{email}, messageSource.getMessage("password_reset", null, getLocale(user)), variables, "reset-password.html", getLocale(user));
        return "Password reset successfully";
    }

    private Locale getLocale(User user) {
        return Locale.getDefault();
    }
}
