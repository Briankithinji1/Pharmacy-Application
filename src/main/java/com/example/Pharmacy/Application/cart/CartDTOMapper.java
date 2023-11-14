package com.example.Pharmacy.Application.cart;

import com.example.Pharmacy.Application.product.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class CartDTOMapper implements Function<CartItem, CartDTO> {

    @Override
    public CartDTO apply(CartItem cartItem) {
        List<ProductDTO> productDTOS = cartItem.getProducts()
                .stream()
                .map(product -> new ProductDTO(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getImageUri(),
//                        product.getCategory().getCategoryId(),
                        product.getCategory().getCategoryName(),
                        product.isAvailable()
                ))
                .toList();

        return new CartDTO(
                cartItem.getCartId(),
                cartItem.getProductDescription(),
                cartItem.getProductPrice(),
                cartItem.getQuantity(),
                cartItem.getTotalPrice(),
                cartItem.getStatus(),
                productDTOS
        );
    }
}
