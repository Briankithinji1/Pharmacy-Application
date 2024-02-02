package com.example.Pharmacy.Application.payment.config;

import com.example.Pharmacy.Application.payment.http.MpesaClient;
import com.example.Pharmacy.Application.payment.service.MpesaAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class MpesaClientConfig {

    private final MpesaAuthenticationService mpesaAuthenticationService;

    @Value("${app.integrations.mpesa.domain}")
    private final String domain;

    @Bean
    MpesaClient mpesaClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(domain)
                .defaultHeader("Authorization", mpesaAuthenticationService.bearer())
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(
                WebClientAdapter.forClient(webClient)
        ).build();
        return factory.createClient(MpesaClient.class);
    }
}
