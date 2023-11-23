package com.example.Pharmacy.Application.order.dao;

import com.example.Pharmacy.Application.cart.model.Cart;
import com.example.Pharmacy.Application.order.enums.OrderStatus;
import com.example.Pharmacy.Application.order.model.Order;
import com.example.Pharmacy.Application.user.model.Customer;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> selectAllOrders();
    Optional<Order> selectAllOrdersByStatus(String status);
    //List<Order> selectAllOrdersByUserId(Long userId); // TODO: implement users and orders
    Optional<Order> selectOrderById(Long orderId);
    List<Order> selectOrdersByProductId(Long productId);
    List<Order> selectAllByCustomerOrderByCreatedDateDesc(Customer customer);
    void insertOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(Long orderId);
    boolean isOrderExistsById(Long orderId);
    //boolean isOrderExistsByUserId(Long userId); // TODO: implement users feature
    boolean isOrderExistsByProductId(Long productId);
    boolean isOrderExistsByStatus(OrderStatus status);
    void updateOrderStatus(Long orderId, OrderStatus status);
}
