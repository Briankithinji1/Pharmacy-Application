package com.brytech.cart_service.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.brytech.cart_service.dto.CartItemDTO;
import com.brytech.cart_service.exception.ApiError;
import com.brytech.cart_service.service.CartItemService;

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
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    @Operation(summary = "Add an item to the cart", description = "Adds a new item to the customer's cart.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Item added to the cart successfully",
            content = @Content(schema = @Schema(implementation = CartItemDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CartItemDTO> addItemToCart(
            @RequestParam @NotNull Long customerId, 
            @RequestBody @Valid CartItemDTO itemDTO
    ) {
        CartItemDTO createdItem = cartItemService.addItemToCart(customerId, itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{cartItemId}") // @PutMapping("/customers/{customerId}/cart-items/{cartItemId}")
    @Operation(summary = "Update a cart item", description = "Updates the quantity of an existing item in the customer's cart.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cart item updated successfully",
            content = @Content(schema = @Schema(implementation = CartItemDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or validation error",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "404", description = "Cart item or customer not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CartItemDTO> updateCartItem(
            @RequestParam @NotNull Long customerId,
            @PathVariable @NotNull Long cartItemId,
            @RequestParam @Positive int newQuantity
    ) {
        CartItemDTO updatedItem = cartItemService.updateCartItem(customerId, cartItemId, newQuantity);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{cartItemId}") // @DeleteMapping("/customers/{customerId}/cart-items/{cartItemId}")
    @Operation(summary = "Remove an item from the cart", description = "Deletes an item from the customer's cart.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cart item removed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "404", description = "Cart item or customer not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> removeCartItem(
            @RequestParam @NotNull Long customerId,
            @PathVariable @NotNull Long cartItemId
    ) {
        cartItemService.removeCartItem(customerId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cartItemId}")
    @Operation(summary = "Find cart item by ID", description = "Fetch a single cart item using its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cart item fetched successfully",
            content = @Content(schema = @Schema(implementation = CartItemDTO.class))),
        @ApiResponse(responseCode = "404", description = "Cart item not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CartItemDTO> findById(
            @PathVariable @NotNull Long cartItemId
    ) {
        CartItemDTO cartItem = cartItemService.findById(cartItemId);
        return ResponseEntity.ok(cartItem);
    }

    @GetMapping("/search")
    @Operation(summary = "Find cart item by cart and product IDs", description = "Search for a cart item using its cart ID and product ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cart item fetched successfully",
            content = @Content(schema = @Schema(implementation = CartItemDTO.class))),
        @ApiResponse(responseCode = "404", description = "Cart item not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<CartItemDTO> findByCartIdAndProductId(
            @RequestParam @NotNull Long cartId,
            @RequestParam @NotNull Long productId
    ) {
        CartItemDTO cartItem = cartItemService.findByCartIdAndProductId(cartId, productId);
        return ResponseEntity.ok(cartItem);
    }

    @GetMapping("/carts/{cartId}/items")
    @Operation(summary = "Find all cart items by cart ID", description = "Retrieve all items associated with a specific cart ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cart items fetched successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CartItemDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Cart not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
})
    public ResponseEntity<List<CartItemDTO>> findByCartId(
            @PathVariable @NotNull Long cartId
    ) {
        List<CartItemDTO> cartItems = cartItemService.findByCartId(cartId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{cartItemId}")
    @Operation(summary = "Delete cart item by ID", description = "Remove a specific cart item from the system using its ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cart item deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Cart item not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteByCartItemId(
            @PathVariable @NotNull Long cartItemId
    ) {
        cartItemService.deleteByCartItemId(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/carts/{cartId}/items")
    @Operation(summary = "Delete all cart items by cart ID", description = "Remove all items associated with a specific cart ID from the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "All cart items deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Cart not found",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteAllByCartId(
            @PathVariable @NotNull Long cartId
    ) {
        cartItemService.deleteAllByCartId(cartId);
        return ResponseEntity.noContent().build();
    }
}
