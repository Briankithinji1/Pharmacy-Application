package com.brytech.pharmacist_service.route;

import java.time.Instant;

import com.brytech.pharmacist_service.model.Outbox;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OutboxEventRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Global error handling
        errorHandler(
                defaultErrorHandler()
                    .maximumRedeliveries(3) // Retry up to 3 times
                    .redeliveryDelay(2000) // 2 second delay between retries
                    .retryAttemptedLogLevel(LoggingLevel.WARN) // Log warnings on retries
                    .onRedelivery(exchange -> {
                        Outbox outbox = exchange.getIn().getBody(Outbox.class);
                        log.warn("Retrying to process Outbox event with ID: {}", outbox.getId());
                    })
        );

        // On Exception block for better control
        onException(Exception.class)
            .maximumRedeliveries(3)
            .redeliveryDelay(2000)
            .useOriginalMessage()
            .logStackTrace(true)
            .handled(true)
            .to("log:com.brytech.pharmacist_service.route.OutboxEventRoute?level=ERROR&showException=true")
            .process(exchange -> {
                Outbox outbox = exchange.getIn().getBody(Outbox.class);
                if (outbox != null) {
                    log.error("Failed to process Outbox event with ID: {}", outbox.getId());
                    outbox.setStatus("Failed");
                    exchange.getIn().setBody(outbox);
                    // Save the failed state back to the database
                    exchange.getIn().setHeader("CamelJpaUseExecuteUpdate", true);
                }
        })
        .to("jpa:com.example.Outbox");


        // Main route
        // Poll for Outbox entries with 'Pending' status every 5 seconds
        from("jpa:com.example.Outbox?consumer.query=select o from Outbox o where o.processed = false and o.status = 'Pending'")
                .routeId("outbox-event-route")
                .log("Processing Outbox event with ID: ${body.id}")

                // Dynamically determine Kafka topic from aggregate_type and event_type
                .process(exchange -> {
                    Outbox outbox = exchange.getIn().getBody(Outbox.class);
                    String topic = "events." + outbox.getAggregateType().toLowerCase() + "." + outbox.getEventType().toString().toLowerCase();

                    // Send payload to Kafka
                    exchange.getIn().setBody(outbox.getPayload());
                    exchange.getIn().setHeader("kafka.TOPIC", topic);

//                    ProducerRecord<String, String> record = new ProducerRecord<>(
//                            topic, outbox.getPayload()
//                    );
                })

                // Send to Kafka and handle success/failure
                .to("kafka:{{kafka.bootstrap-servers}}")
                .log("Successfully sent event to Kafka topic: ${header.kafka.TOPIC} for Outbox ID: ${body.id}")

                // Update status and mark as processed
                .process(exchange -> {
                    Outbox outbox = exchange.getIn().getBody(Outbox.class);
                    outbox.setProcessed(true);
                    outbox.setStatus("Sent");
                    outbox.setSentAt(Instant.now());
                    exchange.getIn().setBody(outbox); // Update Outbox entity
                })

                // Save updated status back to the database
                .to("jpa:com.example.Outbox");
    }
}
