package com.brytech.order_service.repository;

import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.enumeration.PaymentStatus;
import com.brytech.order_service.model.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderReference(String orderReference);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByOrderStatus(OrderStatus orderStatus);
    List<Order> findByPaymentDetails_PaymentStatus(PaymentStatus paymentStatus);
    boolean existById(Long id);
    boolean existsByOrderReference(String orderReference);

    @Query("SELECT o FROM Order o JOIN o.items i WHERE i.productId = :productId AND o.status = :status")
    Page<Order> findPendingOrdersByProductId(@Param("productId") Long productId, @Param("status") OrderStatus status, Pageable pageable);
}
