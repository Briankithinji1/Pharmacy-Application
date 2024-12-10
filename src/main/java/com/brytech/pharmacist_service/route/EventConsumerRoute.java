package com.brytech.pharmacist_service.route;

import com.brytech.pharmacist_service.event.EventHandlerService;
import com.brytech.pharmacist_service.event.consumed_events.PrescriptionCreatedEvent;
import com.brytech.pharmacist_service.exception.RequestValidationException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventConsumerRoute extends RouteBuilder {

    private final EventHandlerService eventHandlerService;

    @Value("${kafka.topic.prescription-created}")
    private String prescriptionCreatedTopic;

    @Value("${kafka.topic.prescription-reviewed}")
    private String prescriptionReviewedTopic;

    @Override
    public void configure() throws Exception {
        onException(RequestValidationException.class)
            .log(LoggingLevel.WARN, "Validation failed for event: ${exception.message}")
            .handled(true)
            .to("kafka:deadLetterTopic");

        onException(Exception.class)
            .log(LoggingLevel.ERROR, "Unexpected error: ${exception.message}")
            .process(exchange -> {
                Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                exchange.getIn().setHeader("errorDetails", exception.getMessage());
            })
            .to("kafka:deadLetterTopic")
            .handled(true);

        // PrescriptionCreatedEvent - from PrescriptionService
        from("kafka:" + prescriptionCreatedTopic)
            .unmarshal().json(JsonLibrary.Jackson, PrescriptionCreatedEvent.class)
            .routeId("prescription-created-consumer")
            .process(exchange -> eventHandlerService.processPrescriptionCreatedEvent(exchange))
            .log("Successfully processed PrescriptionCreatedEvent with ID: ${body.prescriptionId}")
            .to("log:prescriptionCreatedEventProcessed?level=INFO");

        // InventoryUpdatedEvent/ StockUpdatedEvent - from InventoryService
        // LowStockAlerEvent/ DrugShortageEvent - from InventoryService
        // OrderPlacedEvent - from OrderService
        // OrderCanceledEvent - from OrderService
        // MessageReceivedEvent - from MessagingService
        // CustomerProfileUpdatedEvent - from CustomerService
    }
}

