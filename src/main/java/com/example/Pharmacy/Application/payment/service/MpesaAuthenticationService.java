package com.example.Pharmacy.Application.payment.service;

import com.example.Pharmacy.Application.payment.dto.AccessToken;
import com.example.Pharmacy.Application.payment.http.MpesaAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MpesaAuthenticationService {

    private final MpesaAuthClient mpesaAuthClient;
    // used to store access token in memory
    HashMap<String, String> tokenData = new HashMap<>();

    public String accessToken() {
        if (!tokenData.isEmpty() && tokenData.containsKey("access_token") && tokenData.containsKey("expires_in")) {
            // token expires in 1 hour
            // regenerate a minute or less before expiration
            if (LocalDateTime.parse(tokenData.get("expires_in")).isAfter(LocalDateTime.now().plusSeconds(60L))) {
                return tokenData.get("access_token");
            }
        }
        return generateToken();
    }

    private String generateToken() {
        AccessToken accessToken = mpesaAuthClient.generateToken();
        tokenData.put("access_token", accessToken.access_token());
        tokenData.put("expires_in", LocalDateTime.now().plusSeconds(accessToken.expires_in()).toString());
        return accessToken.access_token();
    }

    public String bearer() {
        return "Bearer " + accessToken();
    }
}
