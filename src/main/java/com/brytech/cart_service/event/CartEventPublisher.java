package com.brytech.cart_service.event;

import java.time.Instant;

import com.brytech.cart_service.enumeration.EventType;
import com.brytech.cart_service.model.Outbox;
import com.brytech.cart_service.repository.OutboxRepository;

import org.springframework.stereotype.Component;

@Component
public class CartEventPublisher {

    private final OutboxRepository outboxRepository;
    private final EventService eventService;

    public CartEventPublisher(OutboxRepository outboxRepository, EventService eventService) {
        this.outboxRepository = outboxRepository;
        this.eventService = eventService;
    }
   
    public void publishCartCreatedEvent(CartCreatedEvent event) {
        publishEvent(event, "Cart", EventType.CART_CREATED);
    }

    public void publishCartCheckedOutEvent(CartCheckedOutEvent event) {
        publishEvent(event, "Cart", EventType.CART_CHECKED_OUT);
    }

    public void publishItemAddedToCartEvent(ItemAddedToCartEvent event) {
        publishEvent(event, "Cart", EventType.ITEM_ADDED_TO_CART);
    }

    public void publishItemUpdatedInCartEvent(ItemUpdatedInCartEvent event) {
        publishEvent(event, "Cart", EventType.ITEM_UPDATED_IN_CART);
    }

    public void publishItemRemovedFromCartEvent(ItemRemovedFromCartEvent event) {
        publishEvent(event, "Cart", EventType.ITEM_REMOVED_FROM_CART);
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
