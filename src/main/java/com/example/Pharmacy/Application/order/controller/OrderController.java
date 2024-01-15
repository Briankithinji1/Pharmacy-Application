package com.example.Pharmacy.Application.order.controller;

import com.example.Pharmacy.Application.order.dto.OrderDTO;
import com.example.Pharmacy.Application.order.enums.OrderStatus;
import com.example.Pharmacy.Application.order.service.OrderService;
import com.example.Pharmacy.Application.order.model.Order;
import com.example.Pharmacy.Application.user.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
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

//    @GetMapping("byProductID{productId}")
//    public List<OrderDTO> getOrderByProductId(
//            @PathVariable("productId") Long productId) {
//        return orderService.getOrdersByProductId(productId);
//    }

    @GetMapping("byOrderStatus{status}")
    public Optional<OrderDTO> getOrderByStatus(
            @PathVariable("status") String status) {
        return orderService.getOrderByStatus(status);
    }

    @PostMapping("addOrder")
    public ResponseEntity<?> addOrder(
            @RequestBody Order order) {
        orderService.addOrder(order);
        return ResponseEntity.ok().build();
    }

    // Added Today
    @PostMapping("placeOrder")
    public ResponseEntity<?> placeOrder(
            @RequestBody Customer customer
            ) {
        OrderDTO placedOrder = orderService.placeOrder(customer);
        return new ResponseEntity<>(placedOrder, HttpStatus.CREATED);
    }

    @GetMapping("orderList")
    public ResponseEntity<List<Order>> getAllOrdersList(
            @RequestBody Customer customer
    ) {
        List<Order> orderList = orderService.listOrders(customer);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @DeleteMapping("delete/{orderId}")
    public void deleteOrder(
            @PathVariable("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
    }

    @PutMapping("update/{orderId}")
    public void updateOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody Order order) {
        orderService.updateOrder(orderId, order);
    }

    @PatchMapping("updateStatus/{orderId}")
    public void updateOrderStatus(
            @PathVariable("orderId") Long orderId,
            @RequestBody OrderStatus status) {
        orderService.updateOrderStatus(orderId, status);
    }
}
