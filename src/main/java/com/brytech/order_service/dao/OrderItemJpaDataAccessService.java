package com.brytech.order_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.order_service.model.OrderItem;
import com.brytech.order_service.repository.OrderItemRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("orderItemJpa")
public class OrderItemJpaDataAccessService implements OrderItemDao {

    private final OrderItemRepository orderItemRepository;

    public OrderItemJpaDataAccessService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem save(OrderItem item) {
        return orderItemRepository.save(item);
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id);
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrder_Id(orderId);
    }

    @Override
    public List<OrderItem> findByProductId(Long productId) {
        return orderItemRepository.findByProductId(productId);
    }

    @Override
    public List<OrderItem> findByProductName(String productName) {
        return orderItemRepository.findByProductName(productName);
    }

    @Override
    public List<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId) {
        return orderItemRepository.findByOrder_IdAndProductId(orderId, productId);
    }

    @Override
    public Page<OrderItem> findAll(Pageable pageable) {
        return orderItemRepository.findAll(pageable);
    }

    @Override
    public void saveAll(List<OrderItem> items) {
        orderItemRepository.saveAll(items);
    }

    @Override
    public void deleteItem(Long id) {
        orderItemRepository.deleteById(id); 
    }
}
