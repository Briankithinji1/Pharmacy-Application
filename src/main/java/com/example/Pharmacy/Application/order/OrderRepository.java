package com.example.Pharmacy.Application.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByProductsProductId(Long productId);
    //List<Order> findByUserId(Long userId);

    Optional<OrderItem> findByStatus(String status);

    boolean existsOrderByOrderId(Long orderId);
    //boolean existsOrderByUserId(Long userId); // TODO: check if it should be a list instead of boolean
    boolean existsOrderByStatus(String status);
    boolean existsOrderByProductsProductId(Long productId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Order o SET o.status = ?1 WHERE o.orderId = ?2")
    int updateOrderStatusById(String status, Long orderId);
}
