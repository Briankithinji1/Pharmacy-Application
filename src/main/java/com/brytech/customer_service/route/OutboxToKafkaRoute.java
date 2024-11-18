package com.brytech.customer_service.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OutboxToKafkaRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jdbc:outboxDataSource?useHeadersAsParameters=true&delay=5000")  // Poll outbox every 5 seconds
                .routeId("outbox-to-kafka")
                .onException(Exception.class)
                .log("Error processing message: ${exception.message}")
                .handled(true)
                .end()
                .process(exchange -> {
                    // Transform or enrich the event data if necessary
                    // Example: transform to JSON, set headers, etc.
                })
                .to("kafka:customer-events?brokers=localhost:9092")  // Send to Kafka
                .log("Event sent to Kafka: ${body}");
    }
}
