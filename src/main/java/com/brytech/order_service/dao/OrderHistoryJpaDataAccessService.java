package com.brytech.order_service.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.model.OrderHistory;
import com.brytech.order_service.repository.OrderHistoryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("orderHistoryJpa")
public class OrderHistoryJpaDataAccessService implements OrderHistoryDao {

    private final OrderHistoryRepository historyRepository;

    public OrderHistoryJpaDataAccessService(OrderHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public OrderHistory save(OrderHistory history) {
        return historyRepository.save(history);
    }

    @Override
    public Optional<OrderHistory> findById(Long id) {
        return historyRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        historyRepository.deleteById(id);
    }

    @Override
    public List<OrderHistory> findByOrderId(Long orderId) {
        return historyRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderHistory> findByStatus(OrderStatus status) {
        return historyRepository.findByStatus(status);
    }

    @Override
    public List<OrderHistory> findByUpdatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return historyRepository.findByUpdatedAtBetween(startDate, endDate);
    }

    @Override
    public List<OrderHistory> findByUpdatedBy(String updatedBy) {
        return historyRepository.findByUpdatedBy(updatedBy);
    }

    @Override
    public List<OrderHistory> findByUpdatedByAndUpdatedAtBetween(String updatedBy, LocalDateTime startDate,
            LocalDateTime endDate) {
        return historyRepository.findByUpdatedByAndUpdatedAtBetween(updatedBy, startDate, endDate);
    }

    @Override
    public Optional<OrderHistory> findFirstByOrderIdOrderByUpdatedAtDesc(Long orderId) {
        return historyRepository.findFirstByOrderIdOrderByUpdatedAtDesc(orderId);
    }

    @Override
    public long countByOrderId(Long orderId) {
        return historyRepository.countByOrderId(orderId);
    }

    @Override
    public Page<OrderHistory> findAll(Pageable pageable) {
        return historyRepository.findAll(pageable);
    }
}
