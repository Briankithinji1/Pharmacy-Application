package main.java.com.example.Pharmacy.Application.config.auth;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main.java.com.example.Pharmacy.Application.config.email.EmailService;
import main.java.com.example.Pharmacy.Application.config.password.ResetPasswordRequest;
import main.java.com.example.Pharmacy.Application.exception.DuplicateResourceException;
import main.java.com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            authenticationService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (DuplicateResourceException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration");
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestParam("token") String token) {
        try {
            AuthenticationResponse response = authenticationService.activateAccount(token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException | MessagingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException | ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }

    @PostMapping("refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("resetPassword")
    public String resetPassword(@RequestParam String email, @RequestBody ResetPasswordRequest request) {
        return authenticationService.resetPassword(email, request);
    }
}
