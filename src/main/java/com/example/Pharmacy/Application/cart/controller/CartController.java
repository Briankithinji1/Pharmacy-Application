package main.java.com.example.Pharmacy.Application.cart.controller;

import lombok.RequiredArgsConstructor;
import main.java.com.example.Pharmacy.Application.cart.dto.AddToCartDto;
import main.java.com.example.Pharmacy.Application.cart.dto.CartDTO;
import main.java.com.example.Pharmacy.Application.cart.model.Cart;
import main.java.com.example.Pharmacy.Application.cart.service.CartService;
import main.java.com.example.Pharmacy.Application.product.Product;
import main.java.com.example.Pharmacy.Application.product.ProductDTO;
import main.java.com.example.Pharmacy.Application.product.ProductService;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("cart/{cartId}")
    public CartDTO getCartById(
            @PathVariable("cartId") Long cartId) {
        return cartService.getCartById(cartId);
    }

    @GetMapping("allCarts")
    public List<CartDTO> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("cartItems")
    public ResponseEntity<CartDTO> getCartItems(
            @RequestBody Customer customer
    ) {
        CartDTO cartDTO = cartService.listCartItems(customer);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
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

//    @PostMapping("{addCart}")
//    public ResponseEntity<?> addCart(
//            @RequestBody Cart cart) {
//        cartService.addCart(cart);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("addToCart")
    public ResponseEntity<?> addToCart(
            @RequestBody AddToCartDto addToCartDto
            ) {
        ProductDTO productDTO = productService.getProductById(addToCartDto.productId());
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        cartService.addToCart(addToCartDto, product);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    @DeleteMapping("delete/{cartId}")
    public void deleteCart(
            @PathVariable("cartId") Long cartId) {
        cartService.deleteCart(cartId);
    }

    @DeleteMapping("delete/{cartItemId}")
    public void deleteCartItem(
            @PathVariable("cartItemId") Long cartItemId
    ) {
        cartService.deleteCartItem(cartItemId);
    }

    // ToDO: May be deleted later

    @DeleteMapping("/deleteCartItems/{userId}")
    public ResponseEntity<String> deleteCartItems(@PathVariable Long userId) {
        cartService.deleteCartItems(userId);
        return ResponseEntity.ok("Cart items deleted successfully");
    }

    @DeleteMapping("/deleteUserCartItems")
    public ResponseEntity<String> deleteUserCartItems(@RequestBody Customer customer) {
        cartService.deleteUserCartItems(customer);
        return ResponseEntity.ok("User's cart items deleted successfully");
    }

    @PutMapping("update/{cartId}")
    public void updateCart(
            @PathVariable("cartId") Long cartId,
            @RequestBody Cart cart) {
        cartService.updateCart(cartId, cart);
    }

    @PatchMapping("status/{cartId}")
    public void updateCartStatus(
            @PathVariable("cartId") Long cartId,
            @RequestBody String status) {
        cartService.updateCartStatus(cartId, status);
    }
}
