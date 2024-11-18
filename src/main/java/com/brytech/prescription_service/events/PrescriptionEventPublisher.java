package com.brytech.prescription_service.events;

import java.time.Instant;
import java.util.UUID;

import com.brytech.prescription_service.enums.EventType;
import com.brytech.prescription_service.models.Outbox;
import com.brytech.prescription_service.repository.OutboxRepository;

import org.springframework.stereotype.Component;

@Component
public class PrescriptionEventPublisher {

    private final OutboxRepository outboxRepository;
    private final EventService eventService;

    public PrescriptionEventPublisher(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
        this.eventService = new EventService();
    }

    public void publishPrescriptionCreatedEvent(UUID prescriptionId, PrescriptionCreatedEvent event) {
        try {
            String payload = eventService.convertToJSON(event);

            saveToOutbox(
                    prescriptionId, 
                    "Prescription", 
                    EventType.PRESCRIPTION_CREATED, 
                    payload
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize PrescriptionCreatedEvent", e);
        }
    }


    public void publishPrescriptionUploadedEvent(UUID prescriptionUploadId, PrescriptionUploadedEvent event) {
        try {
            String payload = eventService.convertToJSON(event);

            saveToOutbox(
                prescriptionUploadId,
                "PrescriptionUpload",
                EventType.PRESCRIPTION_UPDATED,
                payload
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize PrescriptionUploadedEvent", e);
        }
    }


    public void publishPrescriptionReviewedEvent(UUID prescriptionId, PrescriptionReviewedEvent event) {
        try {
            String payload = eventService.convertToJSON(event);

            saveToOutbox(
                prescriptionId,
                "Prescription",
                EventType.PRESCRIPTION_UPDATED,
                payload
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize PrescriptionReviewedEvent", e);
        }
    }


    public void publishPrescriptionUpdatedEvent(UUID prescriptionId, PrescriptionUpdatedEvent event) {
        try {
            String payload = eventService.convertToJSON(event);

            saveToOutbox(
                prescriptionId,
                "Prescription",
                EventType.PRESCRIPTION_UPDATED,
                payload
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize PrescriptionReviewedEvent", e);
        }

    }


    public void saveToOutbox(UUID aggregateId, String aggregateType, EventType eventType, String payload) {
        Outbox outbox = new Outbox();
        outbox.setAggragateId(aggregateId);
        outbox.setAggregateType(aggregateType);
        outbox.setEventType(eventType);
        outbox.setPayload(payload);
        outbox.setCreatedAt(Instant.now());
        outbox.setProcessed(false);
        outbox.setStatus("Pending");

        outboxRepository.save(outbox);
    }

}
