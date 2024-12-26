package com.brytech.order_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import com.brytech.order_service.dto.OrderDto;
import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.enumeration.PaymentStatus;
import com.brytech.order_service.exception.ApiError;
import com.brytech.order_service.service.OrderService;

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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create order", description = "Creates a new order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate resource",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto) {
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieves all orders with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class))))
    })
    public ResponseEntity<Page<OrderDto>> findAllOrders(Pageable pageable) {
        Page<OrderDto> orders = orderService.findAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Retrieves an order by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<OrderDto> findOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.findOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/reference/{orderReference}")
    @Operation(summary = "Get order by reference", description = "Retrieves an order by its reference")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<OrderDto> findByOrderReference(@PathVariable String orderReference) {
        OrderDto order = orderService.findByOrderReference(orderReference);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get orders by customer ID", description = "Retrieves orders associated with a specific customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<OrderDto>> findOrdersByCustomerId(@PathVariable Long customerId) {
        List<OrderDto> orders = orderService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get orders by status", description = "Retrieves orders by their status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class))))
    })
    public ResponseEntity<List<OrderDto>> findByOrderStatus(@PathVariable OrderStatus status) {
        List<OrderDto> orders = orderService.findByOrderStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/payment-status/{paymentStatus}")
    @Operation(summary = "Get orders by payment status", description = "Retrieves orders by their payment status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class))))
    })
    public ResponseEntity<List<OrderDto>> findByPaymentStatus(@PathVariable PaymentStatus paymentStatus) {
        List<OrderDto> orders = orderService.findByPaymentStatus(paymentStatus);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/pending-orders/{productId}")
    @Operation(summary = "Get pending orders by product ID", description = "Retrieves pending orders associated with a specific product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Page<OrderDto>> findPendingOrdersByProductId(
            @PathVariable Long productId,
            @RequestParam OrderStatus status,
            Pageable pageable
    ) {
        Page<OrderDto> orders = orderService.findPendingOrdersByProductsId(productId, status, pageable);
        return ResponseEntity.ok(orders);
    }
  
    @PutMapping("/{orderId}")
    @Operation(summary = "Update order", description = "Updates an existing order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order updated successfully",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody @Valid OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(orderId, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Delete order", description = "Deletes an order by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}


