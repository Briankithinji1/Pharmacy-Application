package com.brytech.order_service.repository;

import java.util.List;

import com.brytech.order_service.model.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder_Id(Long orderId);
    List<OrderItem> findByProductId(Long productId);
    List<OrderItem> findByProductName(String productName);
    List<OrderItem> findByOrder_IdAndProductId(Long orderId, Long productId);
}
