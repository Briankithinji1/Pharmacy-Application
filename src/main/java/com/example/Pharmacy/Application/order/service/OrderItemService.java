package main.java.com.example.Pharmacy.Application.order.service;

import jakarta.transaction.Transactional;
import main.java.com.example.Pharmacy.Application.order.model.OrderItem;
import main.java.com.example.Pharmacy.Application.order.repository.OrderItemRepository;
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
