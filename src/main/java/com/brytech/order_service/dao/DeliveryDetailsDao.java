package com.brytech.order_service.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.DeliveryStatus;
import com.brytech.order_service.model.DeliveryDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryDetailsDao {

    DeliveryDetails save(DeliveryDetails details);
    void delete(Long id);
    Optional<DeliveryDetails> findById(Long id);
    List<DeliveryDetails> findByDeliveryStatus(DeliveryStatus status);
    List<DeliveryDetails> findByAddress(String deliveryAddress);
    List<DeliveryDetails> findByCourierId(Long courierId);
    Optional<DeliveryDetails> findByOrderId(Long orderId);
    List<DeliveryDetails> findByEstimatedDeliveryTimeBetween(LocalDateTime start, LocalDateTime end);
    List<DeliveryDetails> findByDeliveredAtBetween(LocalDateTime start, LocalDateTime end);

    void saveAll(List<DeliveryDetails> details);
    Page<DeliveryDetails> findAll(Pageable pageable);
}
