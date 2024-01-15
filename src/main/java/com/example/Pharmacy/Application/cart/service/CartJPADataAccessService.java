package com.example.Pharmacy.Application.cart.service;

import com.example.Pharmacy.Application.cart.repository.CartRepository;
import com.example.Pharmacy.Application.cart.dao.CartDao;
import com.example.Pharmacy.Application.cart.model.Cart;
import com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("cartJPA")
public class CartJPADataAccessService implements CartDao {

    private final CartRepository cartRepository;

    public CartJPADataAccessService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Cart> selectAllCarts() {
        Page<Cart> page = cartRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<Cart> selectCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    // Added Today
    @Override
    public List<Cart> selectAllByCustomerOrderByCreatedDateDesc(Customer customer) {
        return cartRepository.findAllByCustomerOrderByCreatedDateDesc(customer);
    }

    @Override
    public void deleteByUser(Customer customer) {
        cartRepository.deleteByCustomer(customer);
    }

//    @Override
//    public List<Cart> selectCartsByUserId(Long userId) {
//        return cartRepository.findByUserId(userId);
//    }

//    @Override
//    public List<Cart> selectCartByUserIdAndStatus(Long userId, String status) {
//        return cartRepository.findByUserIdAndStatus(userId, status);
//    }

    @Override
    public void insertCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    @Override
    public void deleteCartItems(Long userId) {
        cartRepository.deleteAll();
    }

    @Override
    public void deleteCartItemsByUser(Customer customer) {
        cartRepository.deleteByCustomer(customer);
    }

    @Override
    public boolean isCartExistsById(Long cartId) {
        return !cartRepository.existsCartByCartId(cartId);
    }

//    @Override
//    public boolean isCartExistsByUserId(Long userId) {
//        return cartRepository.existsCartByUserId(userId);
//    }

    @Override
    public boolean isCartExistsByStatus(String status) {
        return cartRepository.existsCartByStatus(status);
    }

    @Override
    public void updateCartStatus(Long cartId, String status) {
        cartRepository.updateCartStatus(cartId, status);
    }
}
