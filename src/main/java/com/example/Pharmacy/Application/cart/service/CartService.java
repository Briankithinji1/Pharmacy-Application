package com.example.Pharmacy.Application.cart.service;

import com.example.Pharmacy.Application.cart.dao.CartDao;
import com.example.Pharmacy.Application.cart.dto.AddToCartDto;
import com.example.Pharmacy.Application.cart.dto.CartDTO;
import com.example.Pharmacy.Application.cart.dto.CartDTOMapper;
import com.example.Pharmacy.Application.cart.dto.CartItemDTO;
import com.example.Pharmacy.Application.cart.exception.CartItemNotExistException;
import com.example.Pharmacy.Application.cart.model.Cart;
import com.example.Pharmacy.Application.exception.RequestValidationException;
import com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import com.example.Pharmacy.Application.product.Product;
import com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    public CartDTO listCartItems(Customer customer) {
        List<Cart> cartList = cartDao.selectAllByCustomerOrderByCreatedDateDesc(customer);
        List<CartItemDTO> cartItems = new ArrayList<>();
        for (Cart cart: cartList) {
            CartItemDTO cartItemDTO = getDtoFromCart(cart);
            cartItems.add(cartItemDTO);
        }
        double totalPrice = 0;
        for (CartItemDTO cartItemDTO: cartItems) {
            totalPrice += (cartItemDTO.product().getPrice()* cartItemDTO.quantity());
        }
        return new CartDTO(cartItems, totalPrice);
    }

    public static CartItemDTO getDtoFromCart(Cart cart) {
        return new CartItemDTO(cart);
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

//    public void addCart(Cart cart) {
//        if (cartDao.isCartExistsById(cart.getCartId())) {
//            throw new ResourceNotFoundException(
//                    "Cart already exists");
//        }
//        cartDao.insertCart(cart);
//    }

    public void addToCart(AddToCartDto addToCartDto, Product product) {
        Cart cart = new Cart(product, addToCartDto.quantity());
        cartDao.insertCart(cart);
    }

    public void deleteCart(Long cartId) {
        if (cartDao.isCartExistsById(cartId)) {
            throw new ResourceNotFoundException(
                    "Cart with id [%s] not found".formatted(cartId)
            );
        }
        cartDao.deleteCart(cartId);
    }

    public void deleteCartItem(Long cartItemId) {
        if (!cartDao.isCartExistsById(cartItemId)) {
            throw new CartItemNotExistException(
                    "Cart is id [%s] invalid".formatted(cartItemId)
            );
        }
        cartDao.deleteCartItem(cartItemId);
    }

    // ToDO: May be deleted later

    public void deleteCartItems(Long userId) {
        cartDao.deleteCartItems(userId);
    }

    public void deleteUserCartItems(Customer customer) {
        cartDao.deleteByUser(customer);
    }

    // Method: deleteCartItem
    // Method: deleteCartItems -> entire cart
    // Method: deleteUserCartItems -> deleted by User

    public void updateCart(Long cartId, Cart cart) {

        Cart carts = cartDao.selectCartById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart with id [%s] not found".formatted(cartId)
                ));

        boolean changes = false;

        if (cart.getQuantity() != null && !cart.getQuantity().equals(carts.getQuantity())) {
            carts.setQuantity(cart.getQuantity());
            changes = true;
        }

        if (cart.getStatus() != null && !cart.getStatus().equals(carts.getStatus())) {
            carts.setStatus(cart.getStatus());
            changes = true;
        }

        if (cart.getTotalPrice() != null && !cart.getTotalPrice().equals(carts.getTotalPrice())) {
            carts.setTotalPrice(cart.getTotalPrice());
            changes = true;
        }

        if (!Objects.equals(cart.getCreatedDate(), carts.getCreatedDate())) {
            carts.setCreatedDate(new Date());
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
        if (cartDao.isCartExistsById(cartId)) {
            throw new ResourceNotFoundException(
                    "Cart with id [%s] not found".formatted(cartId)
            );
        }
        cartDao.updateCartStatus(cartId, status);
    }
}
