package com.brytech.cart_service.service;

import java.time.Instant;
import java.util.List;

import com.brytech.cart_service.dao.CartDao;
import com.brytech.cart_service.dto.CartDTO;
import com.brytech.cart_service.dto.CartItemDTO;
import com.brytech.cart_service.enumeration.CartStatus;
import com.brytech.cart_service.event.CartCreatedEvent;
import com.brytech.cart_service.event.CartEventPublisher;
import com.brytech.cart_service.exception.RequestValidationException;
import com.brytech.cart_service.exception.ResourceNotFoundException;
import com.brytech.cart_service.model.Cart;
import com.brytech.cart_service.model.CartItem;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartDao cartDao;
    private final ModelMapper mapper;
    private final CartEventPublisher eventPublisher;
   
    @Transactional
    public CartDTO createCart(Long customerId) {
        if (cartDao.existsByCustomerIdAndCartStatus(customerId, CartStatus.ACTIVE)) {
            throw new RequestValidationException("Customer already has an active cart");
        }

        Cart newCart = new Cart();
        newCart.setCustomerId(customerId);
        newCart.setCartStatus(CartStatus.ACTIVE);
        newCart.setCreatedAt(Instant.now());
        newCart.setUpdatedAt(Instant.now());

        Cart savedCart = cartDao.save(newCart);

        CartCreatedEvent event = new CartCreatedEvent(
                newCart.getCartId(), 
                savedCart.getCustomerId(), 
                Instant.now());
        try {
            eventPublisher.publishCartCreatedEvent(event);
        } catch (Exception e) {
            log.error("Failed to publish CartCreatedEvent for cartId: {}", savedCart.getCartId(), e);
        }
        
        return toCartDTO(savedCart);
    }

    public CartDTO findById(Long cartId) {
        return cartDao.findById(cartId)
            .map(this::toCartDTO)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart with ID [%s] not found".formatted(cartId)
            ));
    }

    public CartDTO findByCustomerId(Long customerId) {
        return cartDao.findByCustomerId(customerId)
            .map(this::toCartDTO)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart with Customer ID [%s] not found".formatted(customerId)
            ));
    }

    public CartDTO findActiveCartByCustomerId(Long customerId) {
        return cartDao.findActiveCartByCustomerId(customerId)
            .map(this::toCartDTO)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Active cart with Customer ID [%s] not found".formatted(customerId)
            ));
    }

    public List<CartDTO> findAllByCustomerId(Long customerId) {
        return cartDao.findAllByCustomerId(customerId)
            .stream()
            .map(this::toCartDTO)
            .toList();
    }

    public Page<CartDTO> findByCartStatus(CartStatus status, Pageable pageable) {
        return cartDao.findByCartStatus(status, pageable)
            .map(this::toCartDTO);
    }

    public List<CartDTO> findByLastUpdatedBefore(Instant timestamp) {
        return cartDao.findByLastUpdatedBefore(timestamp)
            .stream()
            .map(this::toCartDTO)
            .toList();
    }

    @Transactional
    public CartDTO updateCart(Long cartId, CartDTO cartDTO) {
        Cart cart = cartDao.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart with ID [%s] not found".formatted(cartId)));

        if (cartDTO.status() != null) {
            cart.setCartStatus(cartDTO.status());
        }

        if (cartDTO.totalPrice() != null) {
            cart.setTotalPrice(cartDTO.totalPrice());
        }

        cart.setUpdatedAt(Instant.now());

        Cart updatedCart = cartDao.save(cart);

        return toCartDTO(updatedCart);
    }

    public void deleteCart(Long cartId) {
        cartDao.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart with ID [%s] not found".formatted(cartId)
            ));

        cartDao.deleteCart(cartId);
    }

    public long countByCartStatus(CartStatus status) {
        return cartDao.countByCartStatus(status);
    }

    public CartDTO toCartDTO(Cart cart) {
        return new CartDTO(
            cart.getCartId(),
            cart.getCustomerId(),
            cart.getTotalPrice(),
            cart.getCartStatus(),
            cart.getCreatedAt(),
            cart.getUpdatedAt(),
            cart.getCartItems() == null ? List.of() : cart.getCartItems().stream()
                .map(this::toCartItemDTO)
                .toList()
        );
    }

    public CartItemDTO toCartItemDTO(CartItem cartItem) {
        return mapper.map(cartItem, CartItemDTO.class);
    }
}
