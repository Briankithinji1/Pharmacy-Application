package com.example.Pharmacy.Application.cart.repository;

import com.example.Pharmacy.Application.cart.model.Cart;
import com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    boolean existsCartByCartId(Long cartId);
//    boolean existsCartByUserId(Long userId);
    boolean existsCartByStatus(String status);
    List<Cart> findAllByCustomerOrderByCreatedDateDesc(Customer customer);
    List<Cart> deleteByUser(Customer customer);

//    List<Cart> findByUserId(Long userId);
    List<Cart> findByStatus(String status);
//    List<Cart> findByUserIdAndStatus(Long userId, String status);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Cart c SET c.status = ?1 WHERE c.cartId = ?2")
    void updateCartStatus(Long cartId, String status);
}
