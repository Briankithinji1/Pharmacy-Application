package com.example.Pharmacy.Application.order;

import com.example.Pharmacy.Application.cart.CartItem;
import com.example.Pharmacy.Application.cart.CartService;
import com.example.Pharmacy.Application.exception.RequestValidationException;
import com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final CartService cartService;
    private final OrderDao orderDao;
    private final OrderDTOMapper orderDTOMapper;

    public OrderService(CartService cartService, OrderDao orderDao, OrderDTOMapper orderDTOMapper) {
        this.cartService = cartService;
        this.orderDao = orderDao;
        this.orderDTOMapper = orderDTOMapper;
    }

    public List<OrderDTO> getAllOrders() {
        return orderDao.selectAllOrders()
                .stream()
                .map(orderDTOMapper)
                .toList();
    }

    public OrderDTO getOrderById(Long orderId) {
        return orderDao.selectOrderById(orderId)
                .map(orderDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with id [%s] not found".formatted(orderId)
                ));
    }

//    public List<OrderDTO> getOrdersByUserId(Long userId) {
//        return orderDao.selectAllOrdersByUserId(userId)
//                .stream()
//                .map(orderDTOMapper)
//                .toList();
//    }

    public List<OrderDTO> getOrdersByProductId(Long productId) {
        return orderDao.selectOrdersByProductId(productId)
                .stream()
                .map(orderDTOMapper)
                .toList();
    }

    public Optional<OrderDTO> getOrderByStatus(String status) {
        return orderDao.selectAllOrdersByStatus(status)
                .map(orderDTOMapper);
    }

    public void addOrder(OrderItem orderItem) {
        if (orderDao.isOrderExistsById(orderItem.getOrderId())) {
            throw new ResourceNotFoundException(
                    "Order already exists");
        }

        orderDao.insertOrder(orderItem);
    }

    public void deleteOrder(Long orderId) {
        if (!orderDao.isOrderExistsById(orderId)) {
            throw new ResourceNotFoundException(
                    "Order with id [%s] not found".formatted(orderId)
            );
        }
        orderDao.deleteOrder(orderId);
    }

    public void updateOrder(Long orderId, OrderItem orderItem) {
        OrderItem orders = orderDao.selectOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with id [%s] not found".formatted(orderId)
                ));

        boolean changes = false;

        if (orderItem.getOrderName() != null && !orderItem.getOrderName().equals(orders.getOrderName())) {
            orders.setOrderName(orderItem.getOrderName());
            changes = true;
        }

        if (orderItem.getDescription() != null && !orderItem.getDescription().equals(orders.getDescription())) {
            orders.setDescription(orderItem.getDescription());
            changes = true;
        }

//        if (order.getUserId() != null && !order.getUserId().equals(orders.getUserId())) {
//            orders.setUserId(order.getUserId());
//            changes = true;
//        }

        if (orderItem.getProducts() != null && !orderItem.getProducts().equals(orders.getProducts())) {
            orders.setProducts(orderItem.getProducts());
            changes = true;
        }

        if (orderItem.getQuantity() != null && !orderItem.getQuantity().equals(orders.getQuantity())) {
            orders.setQuantity(orderItem.getQuantity());
            changes = true;
        }

//        if (order.getProductPrice() != null && !order.getProductPrice().equals(orders.getProductPrice())) {
//            orders.setProductPrice(order.getProductPrice());
//            changes = true;
//        }

        if (orderItem.getTotalPrice() != null && !orderItem.getTotalPrice().equals(orders.getTotalPrice())) {
            orders.setTotalPrice(orderItem.getTotalPrice());
            changes = true;
        }

        if (orderItem.getStatus() != null && !orderItem.getStatus().equals(orders.getStatus())) {
            orders.setStatus(orderItem.getStatus());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException(
                    "No changes were made"
            );
        }

        orderDao.updateOrder(orderItem);
    }

    public void updateOrderStatus(Long orderId, String status) {
        if (!orderDao.isOrderExistsById(orderId)) {
            throw new ResourceNotFoundException(
                    "Order with id [%s] not found".formatted(orderId)
            );
        }
        orderDao.updateOrderStatus(orderId, status);
    }
}
