package com.brytech.order_service.service;

import java.time.LocalDateTime;
import java.util.List;

import com.brytech.order_service.dao.OrderHistoryDao;
import com.brytech.order_service.dto.OrderHistoryDTO;
import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.exception.RequestValidationException;
import com.brytech.order_service.exception.ResourceNotFoundException;
import com.brytech.order_service.model.OrderHistory;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final OrderHistoryDao orderHistoryDao;
    private final ModelMapper mapper;

    public OrderHistoryDTO saveOrderHistory(OrderHistoryDTO historyDTO) {
        if (historyDTO == null) {
            throw new RequestValidationException("Order history must not be null");
        }
        OrderHistory history = convertToEntity(historyDTO);
        OrderHistory savedHistory = orderHistoryDao.save(history);
        return convertToDto(savedHistory);
    }

    public OrderHistoryDTO findById(Long id) {
        return orderHistoryDao.findById(id)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException("Order history with ID [%s] not found".formatted(id)));
    }

    public List<OrderHistoryDTO> findByOrderId(Long orderId) {
        if (orderId == null) {
            throw new RequestValidationException("Order ID must not be null");
        }
        return orderHistoryDao.findByOrderId(orderId)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public List<OrderHistoryDTO> findByStatus(OrderStatus status) {
        if (status == null) {
            throw new RequestValidationException("Order status must not be null");
        }
        return orderHistoryDao.findByStatus(status)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public List<OrderHistoryDTO> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new RequestValidationException("Start date and end date must not be null");
        }
        return orderHistoryDao.findByUpdatedAtBetween(startDate, endDate)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public List<OrderHistoryDTO> findByUpdatedBy(String updatedBy) {
        if (updatedBy == null || updatedBy.isBlank()) {
            throw new RequestValidationException("Updated by must not be null or blank");
        }
        return orderHistoryDao.findByUpdatedBy(updatedBy)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public List<OrderHistoryDTO> findByUpdatedByAndUpdatedAtBetween(String updatedBy, LocalDateTime startDate, LocalDateTime endDate) {
        if (updatedBy == null || updatedBy.isBlank()) {
            throw new RequestValidationException("Updated by must not be null or blank");
        }
        if (startDate == null || endDate == null) {
            throw new RequestValidationException("Start date and end date must not be null");
        }
        return orderHistoryDao.findByUpdatedByAndUpdatedAtBetween(updatedBy, startDate, endDate)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public OrderHistoryDTO findMostRecentByOrderId(Long orderId) {
        if (orderId == null) {
            throw new RequestValidationException("Order ID must not be null");
        }
        return orderHistoryDao.findFirstByOrderIdOrderByUpdatedAtDesc(orderId)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException("No history found for order ID [%s]".formatted(orderId)));
    }

    public long countByOrderId(Long orderId) {
        if (orderId == null) {
            throw new RequestValidationException("Order ID must not be null");
        }
        return orderHistoryDao.countByOrderId(orderId);
    }

    public Page<OrderHistoryDTO> findAll(Pageable pageable) {
        if (pageable == null) {
            throw new RequestValidationException("Pageable must not be null");
        }
        return orderHistoryDao.findAll(pageable)
            .map(this::convertToDto);
    }

    public void deleteById(Long id) {
        try {
            orderHistoryDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Order history with ID [%s] not found".formatted(id));
        }
    }

    private OrderHistoryDTO convertToDto(OrderHistory history) {
        return mapper.map(history, OrderHistoryDTO.class);
    }

    private OrderHistory convertToEntity(OrderHistoryDTO historyDTO) {
        return mapper.map(historyDTO, OrderHistory.class);
    }
}
