package main.java.com.example.Pharmacy.Application.payment.http;

import main.java.com.example.Pharmacy.Application.payment.dto.AccessToken;
import org.springframework.web.service.annotation.GetExchange;

public interface MpesaAuthClient {
    @GetExchange("/oauth/v1/generate?grant_type=client_credentials")
    AccessToken generateToken();


}
