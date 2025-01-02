package com.brytech.cart_service.controller;

import java.time.Instant;
import java.util.List;

import jakarta.validation.Valid;

import com.brytech.cart_service.dto.CartDTO;
import com.brytech.cart_service.enumeration.CartStatus;
import com.brytech.cart_service.exception.ApiError;
import com.brytech.cart_service.service.CartService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
    public ResponseEntity<CartDTO> createCart(Long customerId) {
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

    @GetMapping("/customer/{customerId}")
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

    @GetMapping("/customer/{customerId}/active")
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

    @GetMapping("/all/{customerId}")
    @Operation(summary = "Get all carts by Customer ID", description = "Retrieves all carts by the Customer ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carts retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CartDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Carts not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<CartDTO>> findAllByCustomerId(
            @PathVariable("customerId") Long customerId
    ) {
        List<CartDTO> carts =  cartService.findAllByCustomerId(customerId);
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/status")
    @Operation(summary = "Get carts by status", description = "Retrieves carts by their status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carts retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CartDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Carts not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Page<CartDTO>> findByCartStatus(
            @RequestParam CartStatus status, 
            Pageable pageable
    ) {
        Page<CartDTO> cart = cartService.findByCartStatus(status, pageable);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/updated-before")
    @Operation(summary = "Get carts updated before a timestamp", description = "Retrieves carts updated before the given timestamp")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carts retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = CartDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Carts not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<CartDTO>> findCartByLastUpdated (@RequestParam Instant timestamp) {
        List<CartDTO> cart = cartService.findByLastUpdatedBefore(timestamp);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cart", description = "Updates an existing cart by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cart updated successfully",
                content = @Content(schema = @Schema(implementation = CartDTO.class))),
        @ApiResponse(responseCode = "404", description = "Cart not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CartDTO> updateCart(@PathVariable("id") Long id, 
            @RequestBody @Valid CartDTO cartDTO
    ) {
        CartDTO updatedCart = cartService.updateCart(id, cartDTO);
        return ResponseEntity.ok(updatedCart);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Cart", description = "Deletes a cart by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cart deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Cart not found",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    } 

    @GetMapping("/count")
    @Operation(summary = "Get cart count by status", description = "Retrieves a count of carts by status")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cart count retrieved successfully",
                content = @Content(schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "400", description = "Invalid status provided",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Long> countByCartStatus(@RequestParam CartStatus status) {
        long carts = cartService.countByCartStatus(status);
        return ResponseEntity.ok(carts);
    }
}
