package com.brytech.order_service.event.outgoing;

import java.time.Instant;

import com.brytech.order_service.enumeration.EventType;
import com.brytech.order_service.event.AggregateEvent;
import com.brytech.order_service.event.EventService;
import com.brytech.order_service.model.Outbox;
import com.brytech.order_service.repository.OutboxRepository;

import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private final OutboxRepository outboxRepository;
    private final EventService eventService;

    public OrderEventPublisher(OutboxRepository outboxRepository, EventService eventService) {
        this.outboxRepository = outboxRepository;
        this.eventService = eventService;
    }
   
    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        publishEvent(event, "Order", EventType.ORDER_CREATED);
    }

    public void publishOrderUpdatedEvent(OrderUpdatedEvent event) {
        publishEvent(event, "Order", EventType.ORDER_UPDATED);
    }

    public void publishOrderCancelledEVent(OrderCancelledEvent event) {
        publishEvent(event, "Order", EventType.ORDER_CANCELLED);
    }

    public void publishOrderCompletedEvent(OrderCompletedEvent event) {
        publishEvent(event, "Order", EventType.ORDER_COMPLETED);
    }

    public void publishPaymentRequiredEvent(PaymentRequiredEvent event) {
        publishEvent(event, "Payment", EventType.PAYMENT_REQUIRED);
    }

    private void publishEvent(Object event, String aggregateType, EventType eventType) {
        try {
            String payload = eventService.convertToJSON(event);
            saveToOutbox(event, aggregateType, eventType, payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize event of type: " + eventType, e);
        }
    }

    public void saveToOutbox(Object event, String aggregateType, EventType eventType, String payload) {
        if (!(event instanceof AggregateEvent)) {
            throw new IllegalArgumentException(
                    "Event does not implement AggregateEvent: " + event.getClass().getName()
                    );
        }

        Outbox outbox = new Outbox();
        outbox.setAggregateId(getAggregateId((AggregateEvent) event));
        outbox.setAggregateType(aggregateType);
        outbox.setEventType(eventType);
        outbox.setPayload(payload);
        outbox.setCreatedAt(Instant.now());
        outbox.setProcessed(false);
        outbox.setStatus("Pending");

        outboxRepository.save(outbox);
    }

    private Long getAggregateId(AggregateEvent event) {
        return event.getAggregateId();
    }
}
