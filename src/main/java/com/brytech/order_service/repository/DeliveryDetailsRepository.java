package com.brytech.order_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.DeliveryStatus;
import com.brytech.order_service.model.DeliveryDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails, Long> {

    List<DeliveryDetails> findByStatus(DeliveryStatus status);
    List<DeliveryDetails> findByDeliveryAddress(String deliveryAddress);
    List<DeliveryDetails> findByCourierId(Long courierId);
    Optional<DeliveryDetails> findByOrder_Id(Long orderId);
    List<DeliveryDetails> findByEstimatedDeliveryTimeBetween(LocalDateTime start, LocalDateTime end);
    List<DeliveryDetails> findByDeliveredAtBetween(LocalDateTime start, LocalDateTime end);
}
