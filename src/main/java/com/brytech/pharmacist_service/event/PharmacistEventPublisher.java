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
  
    public void publishPrescriptionReviewedEvent(Long reviewId, PrescriptionReviewedEvent event) {
        try {
            String payload = eventService.convertToJSON(event);

            saveToOutbox(
                    reviewId, 
                    "Prescrition_review", 
                    EventType.PRESCRIPTION_REVIEW_COMPLETED, 
                    payload
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize PrescriptionReviewedEvent", e);
        }
    }

    public void publishPrescriptionDispensedEvent(Long prescriptionId, PrescriptionDispensedEvent event) {
        try {
            String payload = eventService.convertToJSON(event);

            saveToOutbox(
                    prescriptionId, 
                    "Prescription", 
                    EventType.PRESCRIPTION_DISPENSED, 
                    payload
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize PrescriptionDispensedEvent", e);
        }
    }

    public void publishInventoryEvent(Long inventoryId, InventoryUpdatedEvent event) {
        try {
            String payload = eventService.convertToJSON(event);

            saveToOutbox(
                    inventoryId, 
                    "Inventory", 
                    EventType.INVENTORY_UPDATED, 
                    payload
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize InventoryUpdatedEvent", e);
        }
    }

    public void publishPrescriptionReadyForPickupEvent(Long prescriptionId, PrescriptionReadyForPickupEvent event) {
        try {
            String payload = eventService.convertToJSON(event);

            saveToOutbox(
                    prescriptionId, 
                    "Prescription", 
                    EventType.PRESCRIPTION_READY_FOR_PICKUP, 
                    payload
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize PrescriptionReadyForPickupEvent", e);
        }
    }

    public void publishPrescriptionErrorEvent(Long prescriptionId, PrescriptionErrorEvent event) {
        try {
            String payload = eventService.convertToJSON(event);

            saveToOutbox(
                    prescriptionId, 
                    "Prescription", 
                    EventType.PRESCRIPTION_ERROR, 
                    payload
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize PrescriptionErrorEvent", e);
        }
    }

    public void publishDrugShortageEvent(Long prescriptionId, DrugShortageEvent event) {
        try {
            String payload = eventService.convertToJSON(event);

            saveToOutbox(
                    prescriptionId, 
                    "Inventory", 
                    EventType.DRUG_SHORTAGE, 
                    payload
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize DrugShortageEvent", e);
        }
    }

    public void saveToOutbox(Long aggregateId, String aggregateType, EventType eventType, String payload) {
        Outbox outbox = new Outbox();
        outbox.setAggregateId(aggregateId);
        outbox.setAggregateType(aggregateType);
        outbox.setEventType(eventType);
        outbox.setPayload(payload);
        outbox.setCreatedAt(Instant.now());
        outbox.setProcessed(false);
        outbox.setStatus("Pending");

        outboxRepository.save(outbox);
    }
}
