package com.example.Pharmacy.Application.cart.dto;

import com.example.Pharmacy.Application.cart.model.Cart;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Service
public class CartDTOMapper implements Function<Cart, CartDTO> {

    @Override
    public CartDTO apply(Cart cart) {
        List<CartItemDTO> cartItemDTOS = mapCartItems(cart);
        return new CartDTO(
                cartItemDTOS,
                cart.getTotalPrice()
        );
    }

    private List<CartItemDTO> mapCartItems(Cart cart) {
        return Collections.singletonList(new CartItemDTO(
                cart.getCartId(),
                cart.getQuantity(),
                cart.getProduct()
        ));
    }
}
