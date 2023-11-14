package com.example.Pharmacy.Application.cart;

import java.util.List;
import java.util.Optional;

public interface CartDao {

    List<CartItem> selectAllCarts();
    Optional<CartItem> selectCartById(Long cartId);
//    List<Cart> selectCartsByUserId(Long userId);
//    List<Cart> selectCartByUserIdAndStatus(Long userId, String status);
    void insertCart(CartItem cartItem);
    void updateCart(CartItem cartItem);
    void deleteCart(Long cartId);
    boolean isCartExistsById(Long cartId);
//    boolean isCartExistsByUserId(Long userId);
    boolean isCartExistsByStatus(String status);
    void updateCartStatus(Long cartId, String status);

}
