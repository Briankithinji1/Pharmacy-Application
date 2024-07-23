package main.java.com.example.Pharmacy.Application.order.repository;

import main.java.com.example.Pharmacy.Application.order.enums.OrderStatus;
import main.java.com.example.Pharmacy.Application.order.model.Order;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

//    List<Order> findByProductsProductId(Long productId);
    //List<Order> findByUserId(Long userId);

    List<Order> findAllByCustomerOrderByOrderDateDesc(Customer customer);

    Optional<Order> findByStatus(String status);

    boolean existsOrderByOrderId(Long orderId);
    //boolean existsOrderByUserId(Long userId); // TODO: check if it should be a list instead of boolean
    boolean existsOrderByStatus(OrderStatus status);
//    boolean existsOrderByProductsProductId(Long productId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Order o SET o.status = ?1 WHERE o.orderId = ?2")
    void updateOrderStatusById(OrderStatus status, Long orderId);
}
