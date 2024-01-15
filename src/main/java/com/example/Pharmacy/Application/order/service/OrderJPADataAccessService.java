package com.example.Pharmacy.Application.order.service;

import com.example.Pharmacy.Application.order.dao.OrderDao;
import com.example.Pharmacy.Application.order.enums.OrderStatus;
import com.example.Pharmacy.Application.order.model.Order;
import com.example.Pharmacy.Application.order.repository.OrderRepository;
import com.example.Pharmacy.Application.user.model.Customer;
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
    public List<Order> selectAllOrders() {
        Page<Order> page = orderRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<Order> selectAllOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

//    @Override
//    public List<Order> selectAllOrdersByUserId(Long userId) {
//        return orderRepository.findByUserId(userId);
//    }

    @Override
    public Optional<Order> selectOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

//    @Override
//    public List<Order> selectOrdersByProductId(Long productId) {
//        return orderRepository.findByProductsProductId(productId);
//    }

    @Override
    public List<Order> selectAllByCustomerOrderByCreatedDateDesc(Customer customer) {
        return orderRepository.findAllByCustomerOrderByOrderDateDesc(customer);
    }

    @Override
    public void insertOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(order);
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

//    @Override
//    public boolean isOrderExistsByProductId(Long productId) {
//        return orderRepository.existsOrderByProductsProductId(productId);
//    }

    @Override
    public boolean isOrderExistsByStatus(OrderStatus status) {
        return orderRepository.existsOrderByStatus(status);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.updateOrderStatusById(status, orderId);
    }
}
