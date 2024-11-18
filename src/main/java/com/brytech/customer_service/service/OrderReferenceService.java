package com.brytech.customer_service.service;

import com.brytech.customer_service.dao.OrderReferenceDAO;
import com.brytech.customer_service.dto.OrderReferenceDTO;
import com.brytech.customer_service.exceptions.ResourceNotFoundException;
import com.brytech.customer_service.model.OrderReference;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderReferenceService {

    private final OrderReferenceDAO referenceDAO;
    private final ModelMapper mapper;

    public OrderReferenceDTO getOrderReferenceByOrderId(UUID id) {
        return referenceDAO.getOrderReferenceByOrderId(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with ID [%s] not found".formatted(id)
                ));
    }

    public Page<OrderReferenceDTO> getOrderReferenceByCustomerId(UUID customer_id, Pageable pageable) {
        return referenceDAO.getOrderReferencesByCustomerId(customer_id, pageable)
                .map(this::convertToDto);
    }

//    public OrderReferenceDTO createOrder(OrderReferenceDTO referenceDTO) {
//        if (referenceDTO == nu)
//
//        OrderReference reference = mapper.map(referenceDTO, OrderReference.class);
//
//        OrderReference savedOrder = referenceDAO.createOrderReference(reference);
//
//        return convertToDto(savedOrder);
//    }

    private OrderReferenceDTO convertToDto(OrderReference orderReference) {
        return mapper.map(orderReference, OrderReferenceDTO.class);
    }
}
