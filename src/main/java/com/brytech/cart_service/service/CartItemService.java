package com.brytech.cart_service.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.brytech.cart_service.dao.CartDao;
import com.brytech.cart_service.dao.CartItemDao;
import com.brytech.cart_service.dto.CartItemDTO;
import com.brytech.cart_service.enumeration.CartStatus;
import com.brytech.cart_service.event.CartEventPublisher;
import com.brytech.cart_service.event.ItemAddedToCartEvent;
import com.brytech.cart_service.event.consumed.ProductCreatedEvent;
import com.brytech.cart_service.exception.RequestValidationException;
import com.brytech.cart_service.exception.ResourceNotFoundException;
import com.brytech.cart_service.model.Cart;
import com.brytech.cart_service.model.CartItem;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemDao itemDao;
    private final CartDao cartDao;
    private final ModelMapper mapper;
    private final CartEventPublisher eventPublisher;
    private final ProductCacheService productCacheService;

    public CartItemDTO addItemToCart(Long customerId, CartItemDTO cartItemDTO) {
        Cart cart = cartDao.findByCustomerId(customerId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setCustomerId(customerId);
                newCart.setCreatedAt(Instant.now());
                newCart.setUpdatedAt(Instant.now());
                newCart.setTotalPrice(BigDecimal.ZERO);
                newCart.setCartStatus(CartStatus.ACTIVE);
                return cartDao.save(newCart);
            });

        ProductCreatedEvent product = productCacheService.getProductById(cartItemDTO.productId());
        if (product == null) {
            throw new ResourceNotFoundException("Product with ID [%s] not found".formatted(cartItemDTO.productId()));
        }

        BigDecimal price = product.price();
        Instant now = Instant.now();

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProductId(product.productId());
        cartItem.setQuantity(cartItemDTO.quantity());
        cartItem.setPrice(price);
        cartItem.setAddedAt(now);
        cartItem.setUpdatedAt(now);

        CartItem savedItem = itemDao.save(cartItem);

        // Update total price
        cart.setTotalPrice(cart.getCartItems()
                .stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        cart.setUpdatedAt(now);
        cartDao.save(cart);

        ItemAddedToCartEvent event = new ItemAddedToCartEvent(
                cart.getCartId(), 
                cartItem.getCartItemId(), 
                cartItem.getProductId(), 
                cartItem.getQuantity(), 
                now);
        eventPublisher.publishItemAddedToCartEvent(event);

        return toCartItemDTO(savedItem);
    }

    public CartItemDTO updateCartItem(Long customerId, Long cartItemId, int newQuantity) {
        if (newQuantity <= 0) {
            throw new RequestValidationException("Quantity must be greater than zero.");
        }
        
        Cart cart = cartDao.findByCustomerId(customerId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart with user ID [%s] not found".formatted(customerId)
            ));

        CartItem cartItem = itemDao.findById(cartItemId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "CartItem with ID [%s] not found".formatted(cartItemId)
            ));

        if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
            throw new RequestValidationException("CartItem does not belong to the customer's cart.");
        }

        cartItem.setQuantity(newQuantity);
        cartItem.setUpdatedAt(Instant.now());
        CartItem updatedItem = itemDao.save(cartItem);

        updateCartTotalPrice(cart);

        return toCartItemDTO(updatedItem);
    }
   
    public void removeCartItem(Long customerId, Long cartItemId) {
        Cart cart = cartDao.findByCustomerId(customerId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart with user ID [%s] not found".formatted(customerId)
            ));

        CartItem cartItem = itemDao.findById(cartItemId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "CartItem with ID [%s] not found".formatted(cartItemId)
            ));

        if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
            throw new IllegalArgumentException("CartItem does not belong to the customer's cart.");
        }

        itemDao.delete(cartItem);

        updateCartTotalPrice(cart);
    }

    private void updateCartTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems()
            .stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(totalPrice);
        cart.setUpdatedAt(Instant.now());
        cartDao.save(cart);
    }

    public CartItemDTO findById(Long cartItemId) {
        return itemDao.findById(cartItemId)
            .map(this::toCartItemDTO)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Item with ID [%s] not found".formatted(cartItemId)
            ));
    }

    public CartItemDTO findByCartIdAndProductId(Long cartId, Long productId) {
        return itemDao.findByCartIdAndProductId(cartId, productId)
            .map(this::toCartItemDTO)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart item with cart ID [%s] and product ID [%s] not found".formatted(cartId, productId)
            ));

    }

    public List<CartItemDTO> findByCartId(Long cartId) {
        return itemDao.findByCartId(cartId)
            .stream()
            .map(this::toCartItemDTO)
            .toList();
    }

    public void deleteByCartItemId(Long cartItemId) {
        itemDao.findById(cartItemId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Item with ID [%s] not found".formatted(cartItemId)
            ));

        itemDao.deleteById(cartItemId);
    }

    public void deleteAllByCartId(Long cartId) {
        cartDao.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart with ID [%s] not found".formatted(cartId)
            ));

        itemDao.deleteAllByCartId(cartId);
    }
   
    private CartItemDTO toCartItemDTO(CartItem cartItem) {
        return mapper.map(cartItem, CartItemDTO.class);
    }
}
