package com.brytech.order_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.enumeration.PaymentStatus;
import com.brytech.order_service.model.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDao {

    Order saveOrder(Order order);
    Page<Order> findAllOrders(Pageable pageable);
    Optional<Order> findById(Long id);
    Optional<Order> findByOrderReference(String orderReference);
    List<Order> findOrdersByCustomerId(Long customerId);
    List<Order> findbyOrderStatus(OrderStatus orderStatus);
    List<Order> findByPaymentStatus(PaymentStatus paymentStatus);
    void deleteOrder(Long id);
    boolean existById(Long id);
    long countOrders();
}
