package com.brytech.cart_service.dao;

import java.util.List;
import java.util.Optional;

import com.brytech.cart_service.model.CartItem;
import com.brytech.cart_service.repository.CartItemRepository;

import org.springframework.stereotype.Repository;

@Repository("cartItemJpa")
public class CartItemJpaDataAccessService implements CartItemDao {

    private final CartItemRepository itemRepository;

    public CartItemJpaDataAccessService(CartItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return itemRepository.save(cartItem);
    }

    @Override
    public Optional<CartItem> findById(Long cartItemId) {
        return itemRepository.findById(cartItemId);
    }

    @Override
    public Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId) {
        return itemRepository.findByCartIdAndProductId(cartId, productId);
    }

    @Override
    public List<CartItem> findByCartId(Long cartId) {
        return itemRepository.findByCartId(cartId);
    }

    @Override
    public void deleteById(Long cartItemId) {
        itemRepository.deleteById(cartItemId);
    }

    @Override
    public void deleteAllByCartId(Long cartId) {
        itemRepository.deleteAllByCartId(cartId);
    }

    @Override
    public boolean existsByCartIdAndProductId(Long cartId, Long productId) {
        return itemRepository.existsByCartIdAndProductId(cartId, productId);
    }

    @Override
    public void delete(CartItem cartItem) {
        itemRepository.delete(cartItem);
    }
}
