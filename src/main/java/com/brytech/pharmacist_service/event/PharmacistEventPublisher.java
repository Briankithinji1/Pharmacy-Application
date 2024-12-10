package com.brytech.pharmacist_service.event;

import java.time.Instant;

import com.brytech.pharmacist_service.enumeration.EventType;
import com.brytech.pharmacist_service.model.Outbox;
import com.brytech.pharmacist_service.repository.OutboxRepository;

import org.springframework.stereotype.Component;

@Component
public class PharmacistEventPublisher {

    private final OutboxRepository outboxRepository;
    private final EventService eventService;
    
    public PharmacistEventPublisher(OutboxRepository outboxRepository, EventService eventService) {
        this.outboxRepository = outboxRepository;
        this.eventService = eventService;
    }
  
    public void publishPrescriptionReviewedEvent(PrescriptionReviewedEvent event) {
        publishEvent(event, "Prescription_Review", EventType.PRESCRIPTION_REVIEW_COMPLETED);
    }

    public void publishPrescriptionDispensedEvent(PrescriptionDispensedEvent event) {
        publishEvent(event, "Prescription", EventType.PRESCRIPTION_DISPENSED);
    }

    public void publishPrescriptionReadyForPickupEvent(PrescriptionReadyForPickupEvent event) {
        publishEvent(event, "Prescription", EventType.PRESCRIPTION_READY_FOR_PICKUP);
    }

    public void publishPrescriptionRejectedEvent(PrescriptionRejectedEvent event) {
        publishEvent( event, "Prescription", EventType.PRESCRIPTION_REJECTED);
    }

    public void publishPrescriptionErrorEvent(PrescriptionErrorEvent event) {
        publishEvent(event, "Prescription", EventType.PRESCRIPTION_ERROR);
    }

    public void publishDrugShortageEvent(DrugShortageEvent event) {    
        publishEvent(event, "Inventory", EventType.DRUG_SHORTAGE);
    }

    public void publishStockDispensedEvent(StockDispensedEvent event) {
        publishEvent(event, "Inventory", EventType.INVENTORY_DISPENSED);
    }

    public void publishLowStockAlertEvent(LowStockAlertEvent event) {
        publishEvent(event, "Inventory", EventType.LOW_STOCK_ALERT);
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
        if (!(event instanceof AggregateEvent aggregateEvent)) {
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
