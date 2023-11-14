package com.example.Pharmacy.Application.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("{cartId}")
    public CartDTO getCartById(
            @PathVariable("cartId") Long cartId) {
        return cartService.getCartById(cartId);
    }

    @GetMapping("{allCarts}")
    public List<CartDTO> getAllCarts() {
        return cartService.getAllCarts();
    }

//    @GetMapping("{userId}")
//    public CartDTO getCartByUserId(
//            @PathVariable("userId") Long userId) {
//        return cartService.getCartByUserId(userId);
//    }

//    @GetMapping("{userId}/{status}")
//    public CartDTO getCartByUserIdAndStatus(
//            @PathVariable("userId") Long userId,
//            @PathVariable("status") String status) {
//        return cartService.getCartByUserIdAndStatus(userId, status);
//    }

    @PostMapping("{addCart}")
    public ResponseEntity<?> addCart(
            @RequestBody CartItem cartItem) {
        cartService.addCart(cartItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{cartId}/delete")
    public void deleteCart(
            @PathVariable("cartId") Long cartId) {
        cartService.deleteCart(cartId);
    }

    @PutMapping("{cartId}/update")
    public void updateCart(
            @PathVariable("cartId") Long cartId,
            @RequestBody CartItem cartItem) {
        cartService.updateCart(cartId, cartItem);
    }

    @PatchMapping("{cartId}/status")
    public void updateCartStatus(
            @PathVariable("cartId") Long cartId,
            @RequestBody String status) {
        cartService.updateCartStatus(cartId, status);
    }
}
