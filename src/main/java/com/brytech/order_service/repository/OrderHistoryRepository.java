package com.brytech.order_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.model.OrderHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    List<OrderHistory> findByOrderId(Long orderId);
    List<OrderHistory> findByStatus(OrderStatus status);
    List<OrderHistory> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<OrderHistory> findByUpdatedBy(String updatedBy);
    List<OrderHistory> findByUpdatedByAndUpdatedAtBetween(String updatedBy, LocalDateTime startDate, LocalDateTime endDate);
    Optional<OrderHistory> findFirstByOrderIdOrderByUpdatedAtDesc(Long orderId);
    long countByOrderId(Long orderId);
}
