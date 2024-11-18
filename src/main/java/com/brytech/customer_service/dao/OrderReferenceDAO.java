package com.brytech.customer_service.dao;

import com.brytech.customer_service.model.OrderReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderReferenceDAO {

    OrderReference createOrderReference(OrderReference orderReference);
    Optional<OrderReference> getOrderReferenceByOrderId(UUID orderId);
    Page<OrderReference> getOrderReferencesByCustomerId(UUID customerId, Pageable pageable);
    OrderReference updateOrderStatus(UUID orderId, String newStatus);
    void deleteOrderReference(UUID orderId);
    List<OrderReference> getOrdersWithinDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
