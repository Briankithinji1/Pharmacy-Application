package com.example.Pharmacy.Application.cart;

import com.example.Pharmacy.Application.exception.RequestValidationException;
import com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import com.example.Pharmacy.Application.product.Product;
import com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartDao cartDao;
    private final CartDTOMapper cartDTOMapper;

    public CartService(CartDao cartDao, CartDTOMapper cartDTOMapper) {
        this.cartDao = cartDao;
        this.cartDTOMapper = cartDTOMapper;
    }

    public List<CartDTO> getAllCarts() {
        return cartDao.selectAllCarts()
                .stream()
                .map(cartDTOMapper)
                .toList();
    }

    public CartDTO getCartById(Long cartId) {
        return cartDao.selectCartById(cartId)
                .map(cartDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart with id [%s] not found".formatted(cartId)
                ));
    }

//    public List<CartDTO> getCartsByUserId(Long userId) {
//        return cartDao.selectCartByUserId(userId)
//                .stream()
//                .map(cartDTOMapper)
//                .toList();
//    }

//    public List<CartDTO> getCartsByUserIdAndStatus(Long userId, String status) {
//        return cartDao.selectCartByUserIdAndStatus(userId, status)
//                .stream()
//                .map(cartDTOMapper)
//                .toList();
//    }

    public void addCart(CartItem cartItem) {
        if (cartDao.isCartExistsById(cartItem.getCartId())) {
            throw new ResourceNotFoundException(
                    "Cart already exists");
        }
        cartDao.insertCart(cartItem);
    }

    public void deleteCart(Long cartId) {
        if (!cartDao.isCartExistsById(cartId)) {
            throw new ResourceNotFoundException(
                    "Cart with id [%s] not found".formatted(cartId)
            );
        }
        cartDao.deleteCart(cartId);
    }

    public void updateCart(Long cartId, CartItem cartItem) {

        CartItem carts = cartDao.selectCartById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart with id [%s] not found".formatted(cartId)
                ));

        boolean changes = false;

        if (cartItem.getQuantity() != null && !cartItem.getQuantity().equals(carts.getQuantity())) {
            carts.setQuantity(cartItem.getQuantity());
            changes = true;
        }

        if (cartItem.getStatus() != null && !cartItem.getStatus().equals(carts.getStatus())) {
            carts.setStatus(cartItem.getStatus());
            changes = true;
        }

        if (cartItem.getProducts() != null && !cartItem.getProducts().equals(carts.getProducts())) {
            carts.setProducts(cartItem.getProducts());
            changes = true;
        }

        if (cartItem.getProductPrice() != null && !cartItem.getProductPrice().equals(carts.getProductPrice())) {
            carts.setProductPrice(cartItem.getProductPrice());
            changes = true;
        }

        if ((cartItem.getProductDescription()) != null && !cartItem.getProductDescription().equals(carts.getProductDescription())) {
            carts.setProductDescription(cartItem.getProductDescription());
            changes = true;
        }

        if (cartItem.getTotalPrice() != null && !cartItem.getTotalPrice().equals(carts.getTotalPrice())) {
            carts.setTotalPrice(cartItem.getTotalPrice());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException(
                    "No changes detected"
            );
        }

        cartDao.updateCart(carts);
    }

    public void updateCartStatus(Long cartId, String status) {
        if (!cartDao.isCartExistsById(cartId)) {
            throw new ResourceNotFoundException(
                    "Cart with id [%s] not found".formatted(cartId)
            );
        }
        cartDao.updateCartStatus(cartId, status);
    }
}
