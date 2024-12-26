package com.brytech.order_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;

import com.brytech.order_service.dto.OrderHistoryDTO;
import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.exception.ApiError;
import com.brytech.order_service.service.OrderHistoryService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/order-history")
@RequiredArgsConstructor
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;


    @PostMapping
    @Operation(summary = "Create order history", description = "Creates a new order history record")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order history created successfully",
                    content = @Content(schema = @Schema(implementation = OrderHistoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<OrderHistoryDTO> saveOrderHistory(@RequestBody @Valid OrderHistoryDTO historyDTO) {
        OrderHistoryDTO savedHistory = orderHistoryService.saveOrderHistory(historyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHistory);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order history by ID", description = "Retrieves an order history record by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order history retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OrderHistoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order history not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<OrderHistoryDTO> getOrderHistoryById(@PathVariable Long id) {
        OrderHistoryDTO history = orderHistoryService.findById(id);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get order histories by order ID", description = "Retrieves all order histories for a given order ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order histories retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderHistoryDTO.class))))
    })
    public ResponseEntity<List<OrderHistoryDTO>> getOrderHistoryByOrderId(@PathVariable Long orderId) {
        List<OrderHistoryDTO> historyList = orderHistoryService.findByOrderId(orderId);
        return ResponseEntity.ok(historyList);
    }

    @GetMapping("/status")
    @Operation(summary = "Get order histories by status", description = "Retrieves order histories filtered by status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order histories retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderHistoryDTO.class))))
    })
    public ResponseEntity<List<OrderHistoryDTO>> getOrderHistoryByStatus(@RequestParam OrderStatus status) {
        List<OrderHistoryDTO> historyList = orderHistoryService.findByStatus(status);
        return ResponseEntity.ok(historyList);
    }

    @GetMapping("/updated-at")
    @Operation(summary = "Get order histories within a date range", description = "Retrieves order histories updated between specified start and end dates")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order histories retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderHistoryDTO.class))))
    })
    public ResponseEntity<List<OrderHistoryDTO>> getOrderHistoryByUpdatedAtBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<OrderHistoryDTO> historyList = orderHistoryService.findByUpdatedAtBetween(startDate, endDate);
        return ResponseEntity.ok(historyList);
    }

    @GetMapping("/updated-by")
    @Operation(summary = "Get order histories by updater", description = "Retrieves order histories updated by a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order histories retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderHistoryDTO.class))))
    })
    public ResponseEntity<List<OrderHistoryDTO>> getOrderHistoryByUpdatedBy(@RequestParam String updatedBy) {
        List<OrderHistoryDTO> historyList = orderHistoryService.findByUpdatedBy(updatedBy);
        return ResponseEntity.ok(historyList);
    }

    @GetMapping("/order/{orderId}/most-recent")
    @Operation(summary = "Get most recent order history", description = "Retrieves the most recent order history for a given order ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Most recent order history retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OrderHistoryDTO.class)))
    })
    public ResponseEntity<OrderHistoryDTO> getMostRecentOrderHistoryByOrderId(@PathVariable Long orderId) {
        OrderHistoryDTO history = orderHistoryService.findMostRecentByOrderId(orderId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/order/{orderId}/count")
    @Operation(summary = "Count order histories by order ID", description = "Counts the total number of order histories for a given order ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Long.class)))
    })
    public ResponseEntity<Long> countOrderHistoryByOrderId(@PathVariable Long orderId) {
        long count = orderHistoryService.countByOrderId(orderId);
        return ResponseEntity.ok(count);
    }

    @GetMapping
    @Operation(summary = "Get all order histories", description = "Retrieves all order histories with pagination")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order histories retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderHistoryDTO.class))))
    })
    public ResponseEntity<Page<OrderHistoryDTO>> getAllOrderHistories(Pageable pageable) {
        Page<OrderHistoryDTO> historyPage = orderHistoryService.findAll(pageable);
        return ResponseEntity.ok(historyPage);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order history", description = "Deletes an order history record by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Order history deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order history not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> deleteOrderHistory(@PathVariable Long id) {
        orderHistoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
