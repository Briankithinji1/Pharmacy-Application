package com.example.Pharmacy.Application.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("orderJPA")
public class OrderJPADataAccessService implements OrderDao {

    private final OrderRepository orderRepository;

    public OrderJPADataAccessService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderItem> selectAllOrders() {
        Page<OrderItem> page = orderRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<OrderItem> selectAllOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

//    @Override
//    public List<Order> selectAllOrdersByUserId(Long userId) {
//        return orderRepository.findByUserId(userId);
//    }

    @Override
    public Optional<OrderItem> selectOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<OrderItem> selectOrdersByProductId(Long productId) {
        return orderRepository.findByProductsProductId(productId);
    }

    @Override
    public void insertOrder(OrderItem orderItem) {
        orderRepository.save(orderItem);
    }

    @Override
    public void updateOrder(OrderItem orderItem) {
        orderRepository.save(orderItem);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public boolean isOrderExistsById(Long orderId) {
        return orderRepository.existsOrderByOrderId(orderId);
    }

//    @Override
//    public boolean isOrderExistsByUserId(Long userId) {
//        return orderRepository.existsOrderByUserId(userId);
//    }

    @Override
    public boolean isOrderExistsByProductId(Long productId) {
        return orderRepository.existsOrderByProductsProductId(productId);
    }

    @Override
    public boolean isOrderExistsByStatus(String status) {
        return orderRepository.existsOrderByStatus(status);
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        orderRepository.updateOrderStatusById(status, orderId);
    }
}
