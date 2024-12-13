package com.brytech.order_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.enumeration.PaymentStatus;
import com.brytech.order_service.model.Order;
import com.brytech.order_service.repository.OrderRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("orderJpa")
public class OrderJpaDataAccessService implements OrderDao {

    private final OrderRepository orderRepository;

    public OrderJpaDataAccessService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> findAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<Order> findByOrderReference(String orderReference) {
        return orderRepository.findByOrderReference(orderReference);
    }

    @Override
    public List<Order> findOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> findByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findByOrderStatus(orderStatus);
    }

    @Override
    public List<Order> findByPaymentStatus(PaymentStatus paymentStatus) {
        return orderRepository.findByPaymentDetails_PaymentStatus(paymentStatus);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return orderRepository.existById(id);
    }

    @Override
    public long countOrders() {
        return orderRepository.count();
    }

    @Override
    public List<Order> findPendingOrdersByProductId(Long productId, OrderStatus status) {
        return orderRepository.findPendingOrdersByProductId(productId, status);
    }
}
