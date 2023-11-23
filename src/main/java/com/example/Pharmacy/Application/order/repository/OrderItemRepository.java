package com.example.Pharmacy.Application.order.repository;

import com.example.Pharmacy.Application.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
