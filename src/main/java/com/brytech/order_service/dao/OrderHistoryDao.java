package com.brytech.order_service.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.model.OrderHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderHistoryDao {

    OrderHistory save(OrderHistory history);
    Optional<OrderHistory> findById(Long id);
    void deleteById(Long id);
    List<OrderHistory> findByOrderId(Long orderId);
    List<OrderHistory> findByStatus(OrderStatus status);
    List<OrderHistory> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<OrderHistory> findByUpdatedBy(String updatedBy);
    List<OrderHistory> findByUpdatedByAndUpdatedAtBetween(String updatedBy, LocalDateTime startDate, LocalDateTime endDate);
    Optional<OrderHistory> findFirstByOrderIdOrderByUpdatedAtDesc(Long orderId);
    long countByOrderId(Long orderId);
    Page<OrderHistory> findAll(Pageable pageable);
}
