package com.brytech.product_service.event;

import java.time.Instant;

import com.brytech.product_service.enumeration.EventType;
import com.brytech.product_service.model.Outbox;
import com.brytech.product_service.repository.OutboxRepository;

import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {

    private final OutboxRepository outboxRepository;
    private final EventService eventService;

    public ProductEventPublisher(OutboxRepository outboxRepository, EventService eventService) {
        this.outboxRepository = outboxRepository;
        this.eventService = eventService;
    }

    public void publishProductCreatedEvent(ProductCreatedEvent event) {
        publishEvent(event, "Product", EventType.PRODUCT_CREATED);
    }

    public void publishProductUpdatedEvent(ProductUpdatedEvent event) {
        publishEvent(event, "Product", EventType.PRODUCT_UPDATED);
    }

    public void publishProductDeletedEvent(ProductDeletedEvent event) {
        publishEvent(event, "Product", EventType.PRODUCT_DELETED);
    }

    public void publishProductPriceChangedEvent(ProductPriceChangedEvent event) {
        publishEvent(event, "Product", EventType.PRODUCT_PRICE_CHANGED);
    }

    public void publishProductAvailabilityChangedEvent(ProductAvailabilityChangedEvent event) {
        publishEvent(event, "Product", EventType.PRODUCT_AVAILABILITY_CHANGED);
    }

    public void publishProductCategoryAddedEvent(ProductCategoryAddedEvent event) {
        publishEvent(event, "Category", EventType.PRODUCT_CATEGORY_ADDED);
    }

    public void publishProductCategoryRemovedEvent(ProductCategoryRemovedEvent event) {
        publishEvent(event, "Category", EventType.PRODUCT_CATEGORY_REMOVED);
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
