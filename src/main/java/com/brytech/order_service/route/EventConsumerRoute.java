package com.brytech.order_service.route;

import com.brytech.order_service.event.incoming.*;
import com.brytech.order_service.exception.RequestValidationException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventConsumerRoute extends RouteBuilder {

    private final EventHandlerService eventHandlerService;

    @Value("${kafka.topic.payment-processed}")
    private String paymentProcessedTopic;

    @Value("${kafka.topic.delivery-scheduled}")
    private String deliveryScheduledTopic;

    @Value("${kafka.topic.prescription-validated}")
    private String prescriptionValidatedTopic;

    @Value("${kafka.topic.delivery-completed}")
    private String deliveryCompletedTopic;

    @Value("${kafka.topic.inventory-updated}")
    private String inventoryUpdatedTopic;

    public EventConsumerRoute(EventHandlerService eventHandlerService) {
        this.eventHandlerService = eventHandlerService;
    }

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

        from("kafka:" + paymentProcessedTopic)
                .unmarshal().json(JsonLibrary.Jackson, PaymentProcessedEvent.class)
                .routeId("payment-processed-consumer")
                .process(exchange -> {
                    PaymentProcessedEvent event = exchange.getIn().getBody(PaymentProcessedEvent.class);
                    eventHandlerService.handlePaymentProcessedEvent(event);
                })
                .log("Successfully processed PaymentProcessedEvent with orderId: ${body.orderId}")
                .to("log:paymentProcessedEventProcessed?level=INFO");

        from("kafka:" + deliveryScheduledTopic)
                .unmarshal().json(JsonLibrary.Jackson, DeliveryScheduledEvent.class)
                .routeId("delivery-scheduled-consumer")
                .process(exchange -> {
                    DeliveryScheduledEvent event = exchange.getIn().getBody(DeliveryScheduledEvent.class);
                    eventHandlerService.handleDeliveryScheduledEvent(event);
                })
                .log("Successfully processed DeliveryScheduledEvent with orderId: ${body.orderId}")
                .to("log:deliveryScheduledEventProcessed?level=INFO");

        from("kafka:" + prescriptionValidatedTopic)
                .unmarshal().json(JsonLibrary.Jackson, PrescriptionValidatedEvent.class)
                .routeId("prescription-validated-consumer")
                .process(exchange -> {
                    PrescriptionValidatedEvent event = exchange.getIn().getBody(PrescriptionValidatedEvent.class);
                    eventHandlerService.handlePrescriptionValidatedEvent(event);
                })
                .log("Successfully processed PrescriptionValidatedEvent with orderId: ${body.orderId}")
                .to("log:prescriptionValidatedEventProcessed?level=INFO");

        from("kafka:" + deliveryCompletedTopic)
                .unmarshal().json(JsonLibrary.Jackson, DeliveryCompletedEvent.class)
                .routeId("delivery-completed-consumer")
                .process(exchange -> {
                    DeliveryCompletedEvent event = exchange.getIn().getBody(DeliveryCompletedEvent.class);
                    eventHandlerService.handleDeliveryCompletedEvent(event);
                })
                .log("Successfully processed DeliveryCompletedEvent with orderId: ${body.orderId}")
                .to("log:deliveryCompletedEventProcessed?level=INFO");

        from("kafka:" + inventoryUpdatedTopic)
                .unmarshal().json(JsonLibrary.Jackson, InventoryUpdatedEvent.class)
                .routeId("inventory-updated-consumer")
                .process(exchange -> {
                    InventoryUpdatedEvent event = exchange.getIn().getBody(InventoryUpdatedEvent.class);
                    eventHandlerService.handleInventoryUpdatedEvent(event);
                })
                .log("Successfully processed InventoryUpdatedEvent with orderId: ${body.orderId}")
                .to("log:inventoryUpdatedEventProcessed?level=INFO");
    }
}
