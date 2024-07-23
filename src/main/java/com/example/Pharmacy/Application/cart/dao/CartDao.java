package main.java.com.example.Pharmacy.Application.cart.dao;

//import com.example.Pharmacy.Application.cart.model.Cart;
//import com.example.Pharmacy.Application.user.model.Customer;
import main.java.com.example.Pharmacy.Application.cart.model.Cart;
import main.java.com.example.Pharmacy.Application.user.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CartDao {

    List<Cart> selectAllCarts();
    Optional<Cart> selectCartById(Long cartId);
    List<Cart> selectAllByCustomerOrderByCreatedDateDesc(Customer customer);
    void deleteByUser(Customer customer);
//    List<Cart> selectCartsByUserId(Long userId);
//    List<Cart> selectCartByUserIdAndStatus(Long userId, String status);
    void insertCart(Cart cart);
    void updateCart(Cart cart);
    void deleteCart(Long cartId);
    void deleteCartItem(Long cartItemId);
    void deleteCartItems(Long userId);
    void deleteCartItemsByUser(Customer customer);
    boolean isCartExistsById(Long cartId);
//    boolean isCartExistsByUserId(Long userId);
    boolean isCartExistsByStatus(String status);
    void updateCartStatus(Long cartId, String status);

}
