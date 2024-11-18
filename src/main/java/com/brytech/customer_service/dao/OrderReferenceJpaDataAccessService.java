package com.brytech.customer_service.dao;

import com.brytech.customer_service.exceptions.ResourceNotFoundException;
import com.brytech.customer_service.model.OrderReference;
import com.brytech.customer_service.repository.OrderReferenceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("orderReferenceJpa")
public class OrderReferenceJpaDataAccessService implements OrderReferenceDAO{

    private final OrderReferenceRepository repository;

    public OrderReferenceJpaDataAccessService(OrderReferenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderReference createOrderReference(OrderReference orderReference) {
        return repository.save(orderReference);
    }

    @Override
    public Optional<OrderReference> getOrderReferenceByOrderId(UUID orderId) {
        return repository.findById(orderId);
    }

    @Override
    public Page<OrderReference> getOrderReferencesByCustomerId(UUID customerId, Pageable pageable) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }
        return repository.findByCustomerId(customerId, pageable);
    }

    @Override
    public OrderReference updateOrderStatus(UUID orderId, String newStatus) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID must not be null");
        }
        if (newStatus == null || newStatus.isBlank()) {
            throw new IllegalArgumentException("New status must not be null or empty");
        }

        // TODO: Move logic to OrderReferenceService
        OrderReference order = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        order.setOrder_status(newStatus);

        return repository.save(order);
    }

    @Override
    public void deleteOrderReference(UUID orderId) {
        repository.deleteById(orderId);
    }

    @Override
    public List<OrderReference> getOrdersWithinDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        Page<OrderReference> page = repository.findByOrderDateBetween(startDate, endDate, pageable);
        return page.getContent();
    }
}
