package com.brytech.order_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.order_service.model.OrderItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemDao {

    OrderItem save(OrderItem item);
    Optional<OrderItem> findById(Long id);
    List<OrderItem> findByOrderId(Long orderId);
    List<OrderItem> findByProductId(Long productId);
    List<OrderItem> findByProductName(String productName);
    List<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);
    Page<OrderItem> findAll(Pageable pageable);
    List<OrderItem> saveAll(List<OrderItem> items);
    void deleteItem(Long id);
}
