package com.example.Pharmacy.Application.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("byID{orderId}")
    public OrderDTO getOrderById(
            @PathVariable("orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("allOrders")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

//    @GetMapping("{userId}")
//    public OrderDTO getOrderByUserId(
//            @PathVariable("userId") Long userId) {
//        return orderService.getOrdersByUserId(userId);
//    }

    @GetMapping("byProductID{productId}")
    public List<OrderDTO> getOrderByProductId(
            @PathVariable("productId") Long productId) {
        return orderService.getOrdersByProductId(productId);
    }

    @GetMapping("byOrderStatus{status}")
    public Optional<OrderDTO> getOrderByStatus(
            @PathVariable("status") String status) {
        return orderService.getOrderByStatus(status);
    }

    @PostMapping("addOrder")
    public ResponseEntity<?> addOrder(
            @RequestBody OrderItem orderItem) {
        orderService.addOrder(orderItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{orderId}")
    public void deleteOrder(
            @PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
    }

    @PutMapping("update/{orderId}")
    public void updateOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody OrderItem orderItem) {
        orderService.updateOrder(orderId, orderItem);
    }

    @PatchMapping("updateStatus/{orderId}")
    public void updateOrderStatus(
            @PathVariable("orderId") Long orderId,
            @RequestBody String status) {
        orderService.updateOrderStatus(orderId, status);
    }
}
