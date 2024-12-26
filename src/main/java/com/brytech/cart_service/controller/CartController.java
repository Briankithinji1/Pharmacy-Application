package com.brytech.cart_service.controller;

import jakarta.validation.Valid;

import com.brytech.cart_service.dto.CartDTO;
import com.brytech.cart_service.exception.ApiError;
import com.brytech.cart_service.service.CartService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @Operation(summary = "Create a new cart", description = "Adds a new cart to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cart create successfully",
                content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CartDTO> createCart(@Valid Long customerId) {
        CartDTO createdCart = cartService.createCart(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cart by ID", description = "Retrieves a cart by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully",
                content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CartDTO> findCartById(@PathVariable("id") Long cartId) {
        CartDTO cart = cartService.findById(cartId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get cart by Customer ID", description = "Retrieves a cart by the Customer ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully",
                content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CartDTO> findCartByCustomerId(@PathVariable("customerId") Long customerId) {
        CartDTO cart = cartService.findByCustomerId(customerId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get active carts by Customer ID", description = "Retrieves active carts by the Customer ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully",
                content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CartDTO> findActiveCartByCustomerId(
            @PathVariable("customerId") Long customerId
    ) {
        CartDTO cart = cartService.findActiveCartByCustomerId(customerId);
        return ResponseEntity.ok(cart);
    }

    // findAllByCustomerId(customerID) - List
    // findByCartStatus(status, page)
    // findByLastUpdated (timestamp)
    // updateCart
}
