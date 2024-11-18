package com.brytech.customer_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String convertToJSON(Object dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting DTO to JSON", e);
        }
    }
}
