package com.brytech.order_service.repository;

import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.enumeration.PaymentStatus;
import com.brytech.order_service.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderReference(String orderReference);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByOrderStatus(OrderStatus orderStatus);
    List<Order> findByPaymentDetails_PaymentStatus(PaymentStatus paymentStatus);
    boolean existById(Long id);
}
