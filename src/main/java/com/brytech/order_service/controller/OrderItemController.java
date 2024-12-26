package com.brytech.order_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import com.brytech.order_service.dto.OrderItemDTO;
import com.brytech.order_service.exception.ApiError;
import com.brytech.order_service.service.OrderItemService;

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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    @Operation(summary = "Add order item", description = "Adds a new order item")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order item added successfully",
                    content = @Content(schema = @Schema(implementation = OrderItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<OrderItemDTO> addOrderItem(@RequestBody @Valid OrderItemDTO orderItemDTO) {
        OrderItemDTO createdItem = orderItemService.addOrderItem(orderItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "Get order item by ID", description = "Retrieves an order item by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order item retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OrderItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order item not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<OrderItemDTO> findOrderItemById(@PathVariable Long itemId) {
        OrderItemDTO orderItem = orderItemService.findItemsById(itemId);
        return ResponseEntity.ok(orderItem);
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get order items by order ID", description = "Retrieves order items associated with a specific order ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order items retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderItemDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<OrderItemDTO>> findOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItemDTO> orderItems = orderItemService.findByOrderId(orderId);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get order items by product ID", description = "Retrieves order items associated with a specific product ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order items retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderItemDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<OrderItemDTO>> findOrderItemsByProductId(@PathVariable Long productId) {
        List<OrderItemDTO> orderItems = orderItemService.findByProductId(productId);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/product-name/{productName}")
    @Operation(summary = "Get order items by product name", description = "Retrieves order items associated with a specific product name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order items retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderItemDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<OrderItemDTO>> findOrderItemsByProductName(@PathVariable String productName) {
        List<OrderItemDTO> orderItems = orderItemService.findByProductName(productName);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/order-product/{orderId}/{productId}")
    @Operation(summary = "Get order items by order ID and product ID", description = "Retrieves order items associated with a specific order ID and product ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order items retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderItemDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<OrderItemDTO>> findOrderItemsByOrderIdAndProductId(@PathVariable Long orderId, @PathVariable Long productId) {
        List<OrderItemDTO> orderItems = orderItemService.findByOrderIdProductId(orderId, productId);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping
    @Operation(summary = "Get all order items", description = "Retrieves all order items with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order items retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderItemDTO.class))))
    })
    public ResponseEntity<Page<OrderItemDTO>> findAllOrderItems(Pageable pageable) {
        Page<OrderItemDTO> orderItems = orderItemService.findAllItems(pageable);
        return ResponseEntity.ok(orderItems);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Add multiple order items", description = "Adds multiple new order items")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order items added successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderItemDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<OrderItemDTO>> addOrderItems(@RequestBody @Valid List<OrderItemDTO> orderItemDTOs) {
        List<OrderItemDTO> createdItems = orderItemService.saveAll(orderItemDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItems);
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "Update order item", description = "Updates an existing order item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order item updated successfully",
                    content = @Content(schema = @Schema(implementation = OrderItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order item not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long itemId, @RequestBody @Valid OrderItemDTO orderItemDTO) {
        OrderItemDTO updatedItem = orderItemService.updateOrderItem(itemId, orderItemDTO);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Delete order item", description = "Deletes an order item by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Order item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order item not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long itemId) {
        orderItemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
    
}
