package com.example.Pharmacy.Application.order.service;

import com.example.Pharmacy.Application.order.model.OrderItem;
import com.example.Pharmacy.Application.order.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public void addOrderedProducts(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
