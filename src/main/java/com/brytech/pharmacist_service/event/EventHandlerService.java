package com.brytech.pharmacist_service.event;

import java.time.LocalDateTime;

import com.brytech.pharmacist_service.dto.PrescriptionReviewDto;
import com.brytech.pharmacist_service.enumeration.ReviewStatus;
import com.brytech.pharmacist_service.event.consumed_events.PrescriptionCreatedEvent;
import com.brytech.pharmacist_service.exception.RequestValidationException;
import com.brytech.pharmacist_service.service.InventoryClient;
import com.brytech.pharmacist_service.service.PrescriptionReviewService;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventHandlerService {

    private final KafkaTemplate<String, PrescriptionReviewedEvent> kafkaTemplate;
    private final KafkaTemplate<String, StockDispensedEvent> stockDispensedKafkaTemplate;
    private final KafkaTemplate<String, LowStockAlertEvent> lowStockKafkaTemplate;
    private final PrescriptionReviewService reviewService;
    private final InventoryClient inventoryClient;

    @Value("${kafka.topic.prescription-created}")
    private String prescriptionCreatedTopic;

    @Value("${kafka.topic.prescription-reviewed}")
    private String prescriptionReviewedTopic;

    @Value("${kafka.topic.stock-dispensed}")
    private String stockDispensedTopic;

    @Value("${kafka.topic.low-stock-alert}")
    private String lowStockAlertTopic;

    public void processPrescriptionCreatedEvent(Exchange exchange) {
        PrescriptionCreatedEvent event = exchange.getIn().getBody(PrescriptionCreatedEvent.class);

        if (event.prescriptionId() == null || event.customerId() == null) {
            throw new RequestValidationException("Invalid PrescriptionCreatedEvent received");
        }

        PrescriptionReviewDto reviewDto = new PrescriptionReviewDto(
                null, // generated when the entity is persisted
                event.prescriptionId(), 
                ReviewStatus.PENDING, 
                null, // initial notes
                event.createdAt()
        );

        PrescriptionReviewDto savedReview = reviewService.createReview(reviewDto);

        // Produce and send PrescriptionReviewedEvent
        PrescriptionReviewedEvent reviewedEvent = new PrescriptionReviewedEvent(
                savedReview.prescriptionId(), 
                null, 
                savedReview.status(),
                savedReview.notes(), // Optional: Add initial notes
                LocalDateTime.now()
        );

        kafkaTemplate.send(prescriptionReviewedTopic, reviewedEvent);
    }

//    public void handlePrescriptionUpdatedEvent(PrescriptionUpdatedEvent event) {
        // processing logic
//    }
    
//    public void handleInventoryUpdatedEvent(InventoryUpdatedEvent event) {
        // Processing logic for InventoryUpdatedEvent
//         inventoryRepository.updateStockByProductId(event.productId(), event.quantity());
//    }

//    public void handleOrderPlaceEvent(OrderPlacedEvent event) {
        // Processing logic for OrderPlacedEvent
//    }
    

}
