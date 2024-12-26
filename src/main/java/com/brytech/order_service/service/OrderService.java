package com.brytech.order_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotNull;

import com.brytech.order_service.dao.OrderDao;
import com.brytech.order_service.dto.DeliveryDetailsDTO;
import com.brytech.order_service.dto.OrderDto;
import com.brytech.order_service.dto.OrderItemDTO;
import com.brytech.order_service.dto.PaymentDetailsDTO;
import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.enumeration.PaymentStatus;
import com.brytech.order_service.exception.DuplicateResourceException;
import com.brytech.order_service.exception.RequestValidationException;
import com.brytech.order_service.exception.ResourceNotFoundException;
import com.brytech.order_service.model.DeliveryDetails;
import com.brytech.order_service.model.Order;
import com.brytech.order_service.model.OrderHistory;
import com.brytech.order_service.model.OrderItem;
import com.brytech.order_service.model.PaymentDetails;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final ModelMapper mapper;

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        if (orderDao.existsByOrderReference(orderDto.orderReference())) {
            throw new DuplicateResourceException(
                    "Order with reference [%s] already exists".formatted(orderDto.orderReference())
            );
        }

        if (orderDto.items() == null || orderDto.items().isEmpty()) {
            throw new RequestValidationException("Order must have at least one item.");
        }
        if (orderDto.customerId() == null) {
            throw new RequestValidationException("Customer ID cannot be null.");
        }

        Order order = new Order();
        order.setOrderReference(orderDto.orderReference());
        order.setCustomerId(orderDto.customerId());
        order.setItems(orderDto.items().stream()
                .map(orderItemDTO -> convertToOrderItemEntity(orderItemDTO, order))
                .collect(Collectors.toList())
        );
        order.setPaymentDetails(convertToPaymentDetailsEntity(orderDto.paymentDetails()));
        order.setDeliveryDetails(convertToDeliveryDetailsEntity(orderDto.deliveryDetails()));
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        OrderHistory initialHistory = new OrderHistory(OrderStatus.PENDING, LocalDateTime.now());
        initialHistory.setOrder(order);
        order.getOrderHistory().add(initialHistory);

        Order savedOrder = orderDao.saveOrder(order);

        return convertToDto(savedOrder);
    }

    public Page<OrderDto> findAllOrders(Pageable pageable) {
        return orderDao.findAllOrders(pageable)
                .map(this::convertToDto);
    }

    public OrderDto findOrderById(Long orderId) {
        return orderDao.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with ID [%s] not found".formatted(orderId)
                ));
    }

    public OrderDto findByOrderReference(String orderReference) {
        return orderDao.findByOrderReference(orderReference)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with reference [%s] not found".formatted(orderReference)
                ));
    }

    public List<OrderDto> findOrdersByCustomerId(Long customerId) {
        if (customerId == null) {
            throw new RequestValidationException("Customer ID must not be null");
        }

        return orderDao.findOrdersByCustomerId(customerId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<OrderDto> findByOrderStatus(@NotNull OrderStatus status) {
        return orderDao.findByOrderStatus(status)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<OrderDto> findByPaymentStatus(@NotNull PaymentStatus status) {
        return orderDao.findByPaymentStatus(status)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        Order existingOrder = orderDao.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with ID [%s] not found".formatted(orderId)
                ));

        // Update fields
        if (orderDto.orderReference() != null) {
            existingOrder.setOrderReference(orderDto.orderReference());
        }
        if (orderDto.customerId() != null) {
            existingOrder.setCustomerId(orderDto.customerId());
        }
        if (orderDto.items() != null) { // TODO: CREATE THE UPDATE ORDER ITEMS METHOD (Order Item Service)
//            updateOrderItems(existingOrder, orderDto.items());
        }
        if (orderDto.paymentDetails() != null) {
            existingOrder.setPaymentDetails(
                    mapper.map(orderDto.paymentDetails(), PaymentDetails.class)
            );
        }
        if (orderDto.deliveryDetails() != null) {
            existingOrder.setDeliveryDetails(
                    mapper.map(orderDto.deliveryDetails(), DeliveryDetails.class)
            );
        }
        if (orderDto.status() != null) {
            existingOrder.setStatus(orderDto.status());
        }

        existingOrder.setUpdatedAt(LocalDateTime.now());

        Order updatedOrder = orderDao.saveOrder(existingOrder);

        return convertToDto(updatedOrder);
    }

    public void deleteOrder(Long id) {
        try {
            orderDao.deleteOrder(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Order with ID [%s] not found".formatted(id)
            );
        }
    }

    public Page<OrderDto> findPendingOrdersByProductsId(Long productId, OrderStatus status, Pageable pageable) {
        if (productId == null || status == null) {
            throw new RequestValidationException("Product ID and Order status must not be null");
        }

        return orderDao.findPendingOrdersByProductId(productId, status, pageable)
                .map(this::convertToDto);
    }

    private OrderDto convertToDto(Order order) {
        return mapper.map(order, OrderDto.class);
    }

    private OrderItem convertToOrderItemEntity(OrderItemDTO orderItemDTO, Order order) {
        OrderItem orderItem = mapper.map(orderItemDTO, OrderItem.class);
        orderItem.setOrder(order);
        return orderItem;
    }

    private PaymentDetails convertToPaymentDetailsEntity(PaymentDetailsDTO paymentDetailsDTO) {
        return mapper.map(paymentDetailsDTO, PaymentDetails.class);
    }

    private DeliveryDetails convertToDeliveryDetailsEntity(DeliveryDetailsDTO detailsDTO) {
        return mapper.map(detailsDTO, DeliveryDetails.class);
    }
}


