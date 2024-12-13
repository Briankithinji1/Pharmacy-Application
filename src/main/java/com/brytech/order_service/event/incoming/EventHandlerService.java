package com.brytech.order_service.event.incoming;

import com.brytech.order_service.dao.OrderDao;
import com.brytech.order_service.enumeration.OrderStatus;
import com.brytech.order_service.exception.RequestValidationException;
import com.brytech.order_service.exception.ResourceNotFoundException;
import com.brytech.order_service.model.DeliveryDetails;
import com.brytech.order_service.model.Order;

import com.brytech.order_service.model.OrderHistory;
import com.brytech.order_service.model.PaymentDetails;
import com.brytech.order_service.service.PrescriptionService;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventHandlerService {

    private final OrderDao orderDao;
    private final PrescriptionService prescriptionService;

    public void handlePaymentProcessedEvent(PaymentProcessedEvent event) {
        Order order = orderDao.findById(event.orderId())
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Order with ID " + event.orderId() + "not found"
            ));

        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new RequestValidationException("Payment cannot be processed for order in status: " + order.getStatus());
        }

        // Update payment details and status
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAmount(event.amountPaid());
        paymentDetails.setPaymentMethod(event.paymentMethod());
        paymentDetails.setProcessedAt(LocalDateTime.from(event.processedAt()));

        order.setPaymentDetails(paymentDetails);
        order.setStatus(OrderStatus.PAID);
        order.setUpdatedAt(LocalDateTime.now());

        orderDao.saveOrder(order);

        // Initiate fulfillment process (fulfilmentService.processOrder(order));
        // Send notifications (notificationService.sendOrderConfirmationEmail(order));
    }

    public void handlePrescriptionValidatedEvent(PrescriptionValidatedEvent event) {
        Order order = orderDao.findById(event.orderId())
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Order with ID " + event.orderId() + "not found"
            ));

        if (order.getStatus() != OrderStatus.AWAITING_PRESCRIPTION_VALIDATION) {
            throw new RequestValidationException("Prescription validation is not applicable for order in status: " + order.getStatus());
        }

        boolean validationSuccessful = prescriptionService.validatePrescription(event.prescriptionId());

        if (validationSuccessful) {
            order.setStatus(OrderStatus.PRESCRIPTION_VALIDATED);
            order.getOrderHistory().add(new OrderHistory(order, OrderStatus.PRESCRIPTION_VALIDATED, LocalDateTime.now()));
            order.setUpdatedAt(LocalDateTime.now());
            orderDao.saveOrder(order);

            // Trigger further actions, e.g., scheduleDelivery(order)
            // deliveryService.scheduleDelivery(order);
        } else {
            order.setStatus(OrderStatus.CANCELLED);
            order.getOrderHistory().add(new OrderHistory(order, OrderStatus.CANCELLED, LocalDateTime.now()));
            order.setUpdatedAt(LocalDateTime.now());
            orderDao.saveOrder(order);

            // notificationService.notifyCustomer(order.getCustomerId(), "Prescription validation failed. Order has been cancelled.");
        }
    } 

    public void handleInventoryUpdatedEvent(InventoryUpdatedEvent event) {
        // Log or process the inventory update if it affects current orders
        List<Order> affectedOrders = orderDao.findPendingOrdersByProductId(event.productId(), OrderStatus.PENDING);

        for (Order order : affectedOrders) {
            boolean insufficientStock = order.getItems().stream()
                    .anyMatch(item -> item.getProductId().equals(event.productId())
                            && item.getQuantity() > event.quantityAvailable());

            if (insufficientStock) {
                order.setStatus(OrderStatus.PENDING);
                order.getOrderHistory().add(new OrderHistory(order, OrderStatus.PENDING, LocalDateTime.now()));
                order.setUpdatedAt(LocalDateTime.now());
                orderDao.saveOrder(order);

//                notificationService.notifyCustomer(order.getCustomerId(),
//                        "Your order is on hold due to insufficient stock for one or more items.");
            }
        }
    }

    public void handleDeliveryCompletedEvent(DeliveryCompletedEvent event) {
        Order order = orderDao.findById(event.orderId())
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Order with ID " + event.orderId() + " not found"
            ));

        if (order.getStatus() != OrderStatus.SHIPPED) {
            throw new RequestValidationException("Cannot complete delivery for order in status: " + order.getStatus());
        }

        DeliveryDetails deliveryDetails = order.getDeliveryDetails();
        if (deliveryDetails != null) {
            deliveryDetails.setDeliveredAt(LocalDateTime.from(event.deliveredAt()));
            deliveryDetails.setTrackingNumber(event.trackingNumber());
        }

        order.setStatus(OrderStatus.DELIVERED);
        order.getOrderHistory().add(new OrderHistory(order, OrderStatus.DELIVERED, LocalDateTime.now()));
        order.setUpdatedAt(LocalDateTime.now());

        orderDao.saveOrder(order);

        // Send delivery confirmation to customer
       // notificationService.sendDeliveryConfirmation(order);
    }
    
    public void handleDeliveryScheduledEvent(DeliveryScheduledEvent event) {
        Order order = orderDao.findById(event.orderId())
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Order with ID " + event.orderId() + " not found"
            ));

        if (order.getStatus() != OrderStatus.PAID) {
            throw new IllegalStateException("Cannot schedule delivery for order in status: " + order.getStatus());
        }

        DeliveryDetails deliveryDetails = new DeliveryDetails();
        deliveryDetails.setDeliveryAddress(event.deliveryAddress());
        deliveryDetails.setEstimatedDeliveryTime(LocalDateTime.from(event.scheduledDeliveryTime()));

        order.setDeliveryDetails(deliveryDetails);
        order.setStatus(OrderStatus.DELIVERY_SCHEDULED);
        order.getOrderHistory().add(new OrderHistory(order, OrderStatus.DELIVERY_SCHEDULED, LocalDateTime.now()));
        order.setUpdatedAt(LocalDateTime.now());

        orderDao.saveOrder(order);

        // Send delivery notification to customer
        // notificationService.sendDeliveryNotification(order);
    }
}
