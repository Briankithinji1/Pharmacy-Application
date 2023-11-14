package com.example.Pharmacy.Application.cart;

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
    public List<CartItem> selectAllCarts() {
        Page<CartItem> page = cartRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<CartItem> selectCartById(Long cartId) {
        return cartRepository.findById(cartId);
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
    public void insertCart(CartItem cartItem) {
        cartRepository.save(cartItem);
    }

    @Override
    public void updateCart(CartItem cartItem) {
        cartRepository.save(cartItem);
    }

    @Override
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public boolean isCartExistsById(Long cartId) {
        return cartRepository.existsCartByCartId(cartId);
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
