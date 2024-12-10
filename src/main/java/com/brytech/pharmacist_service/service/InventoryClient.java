package com.brytech.pharmacist_service.service;

import java.net.URI;

import com.brytech.pharmacist_service.dto.InventoryDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryClient {

    private static Logger logger = LoggerFactory.getLogger(InventoryClient.class);

    private final RestClient restClient;
    

    public InventoryDto getInventoryByProductId(Long productId) {

        final URI url = UriComponentsBuilder
            .fromHttpUrl("http://inventory-service/api/v1/inventory")
            .path("/products/{productId}")
            .queryParam("productId")
            .buildAndExpand()
            .toUri();

        try {
            return restClient.get()
                .uri(url)
                .headers(h -> h.setBearerAuth(getAuthToken()))
                .retrieve()
                .body(InventoryDto.class);

        } catch (Exception e) {
            logger.error("Error fetching inventory for product ID: {}", productId, e);
            throw new RuntimeException("Failed to fetch inventory details", e);
        }
    }

    private String getAuthToken() {
        // Fetch token
        return "my-secure-token-here";
    }
}
