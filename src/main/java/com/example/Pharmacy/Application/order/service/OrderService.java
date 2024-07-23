package main.java.com.example.Pharmacy.Application.order.service;

import main.java.com.example.Pharmacy.Application.cart.dto.CartDTO;
import main.java.com.example.Pharmacy.Application.cart.dto.CartItemDTO;
import main.java.com.example.Pharmacy.Application.cart.service.CartService;
import main.java.com.example.Pharmacy.Application.exception.RequestValidationException;
import main.java.com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import main.java.com.example.Pharmacy.Application.order.dao.OrderDao;
import main.java.com.example.Pharmacy.Application.order.dto.OrderDTO;
import main.java.com.example.Pharmacy.Application.order.dto.OrderDTOMapper;
import main.java.com.example.Pharmacy.Application.order.enums.OrderStatus;
import main.java.com.example.Pharmacy.Application.order.model.Order;
import main.java.com.example.Pharmacy.Application.order.model.OrderItem;
import main.java.com.example.Pharmacy.Application.order.repository.OrderItemRepository;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final CartService cartService;
    private final OrderDao orderDao;
    private final OrderDTOMapper orderDTOMapper;
    private final OrderItemRepository orderItemRepository;

    public OrderService(CartService cartService, OrderDao orderDao, OrderDTOMapper orderDTOMapper, OrderItemRepository orderItemRepository) {
        this.cartService = cartService;
        this.orderDao = orderDao;
        this.orderDTOMapper = orderDTOMapper;
        this.orderItemRepository = orderItemRepository;
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

//    public List<OrderDTO> getOrdersByProductId(Long productId) {
//        return orderDao.selectOrdersByProductId(productId)
//                .stream()
//                .map(orderDTOMapper)
//                .toList();
//    }

    public Optional<OrderDTO> getOrderByStatus(String status) {
        return orderDao.selectAllOrdersByStatus(status)
                .map(orderDTOMapper);
    }

    public void addOrder(Order order) {
        if (orderDao.isOrderExistsById(order.getOrderId())) {
            throw new ResourceNotFoundException(
                    "Order already exists");
        }

        orderDao.insertOrder(order);
    }

    // Newly Added methods

    // Method To Place an Order
    public OrderDTO placeOrder(Customer customer) {
        // Get cart items for the user
        CartDTO cartDTO = cartService.listCartItems(customer);

        List<CartItemDTO> cartItemDTOList = cartDTO.cartItems();

        // Create order and save
        Order newOrder = new Order();
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setCustomer(customer);
        newOrder.setTotalPrice(BigDecimal.valueOf(cartDTO.totalPrice()));
        newOrder.setStatus(OrderStatus.PENDING); // Initial status
        // ToDo: Add other order fields
        orderDao.insertOrder(newOrder);

        // Update Order Status
        updateOrderStatus(newOrder.getOrderId(), OrderStatus.PROCESSING);

        for (CartItemDTO cartItemDTO: cartItemDTOList) {
            // Create orderItem and save
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(BigDecimal.valueOf(cartItemDTO.product().getPrice()));
            orderItem.setProduct(cartItemDTO.product());
            orderItem.setQuantity(cartItemDTO.quantity());
            orderItem.setOrder(newOrder);
            // Add to order items list
            orderItemRepository.save(orderItem);
        }

        cartService.deleteUserCartItems(customer);
        return orderDTOMapper.apply(newOrder);
    }

    public List<Order> listOrders(Customer customer) {
        return orderDao.selectAllByCustomerOrderByCreatedDateDesc(customer);
    }

    public void deleteOrder(Long orderId) {
        if (!orderDao.isOrderExistsById(orderId)) {
            throw new ResourceNotFoundException(
                    "Order with id [%s] not found".formatted(orderId)
            );
        }

        // TODO: Validation checks and confirmation before order cancellation

        // Update Order Status to CANCEL
        updateOrderStatus(orderId, OrderStatus.CANCELED);

        orderDao.deleteOrder(orderId);
    }

    public void updateOrder(Long orderId, Order order) {
        Order orders = orderDao.selectOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with id [%s] not found".formatted(orderId)
                ));

        boolean changes = false;

//        if (order.getOrderName() != null && !order.getOrderName().equals(orders.getOrderName())) {
//            orders.setOrderName(order.getOrderName());
//            changes = true;
//        }
//
//        if (order.getDescription() != null && !order.getDescription().equals(orders.getDescription())) {
//            orders.setDescription(order.getDescription());
//            changes = true;
//        }

//        if (order.getUserId() != null && !order.getUserId().equals(orders.getUserId())) {
//            orders.setUserId(order.getUserId());
//            changes = true;
//        }

//        if (order.getProducts() != null && !order.getProducts().equals(orders.getProducts())) {
//            orders.setProducts(order.getProducts());
//            changes = true;
//        }

        if (order.getQuantity() != null && !order.getQuantity().equals(orders.getQuantity())) {
            orders.setQuantity(order.getQuantity());
            changes = true;
        }

//        if (order.getProductPrice() != null && !order.getProductPrice().equals(orders.getProductPrice())) {
//            orders.setProductPrice(order.getProductPrice());
//            changes = true;
//        }

        if (order.getTotalPrice() != null && !order.getTotalPrice().equals(orders.getTotalPrice())) {
            orders.setTotalPrice(order.getTotalPrice());
            changes = true;
        }

        if (order.getStatus() != null && !order.getStatus().equals(orders.getStatus())) {
            orders.setStatus(order.getStatus());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException(
                    "No changes were made"
            );
        }

        orderDao.updateOrder(order);
    }

    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderDao.selectOrderById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Order with id [%s] not found".formatted(orderId)
                        ));
        order.setStatus(newStatus);
        orderDao.updateOrder(order);
    }
}
