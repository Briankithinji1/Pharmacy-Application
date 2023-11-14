package com.example.Pharmacy.Application.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<OrderItem> selectAllOrders();
    Optional<OrderItem> selectAllOrdersByStatus(String status);
    //List<Order> selectAllOrdersByUserId(Long userId); // TODO: implement users and orders
    Optional<OrderItem> selectOrderById(Long orderId);
    List<OrderItem> selectOrdersByProductId(Long productId);
    void insertOrder(OrderItem orderItem);
    void updateOrder(OrderItem orderItem);
    void deleteOrder(Long orderId);
    boolean isOrderExistsById(Long orderId);
    //boolean isOrderExistsByUserId(Long userId); // TODO: implement users feature
    boolean isOrderExistsByProductId(Long productId);
    boolean isOrderExistsByStatus(String status);
    void updateOrderStatus(Long orderId, String status);
}
