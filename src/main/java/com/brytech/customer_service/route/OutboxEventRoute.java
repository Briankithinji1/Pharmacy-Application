package com.brytech.customer_service.route;

import com.brytech.customer_service.model.Outbox;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.Instant;

@Component
public class OutboxEventRoute extends RouteBuilder {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure() throws Exception {

        // Poll for Outbox entries with 'Pending' status every 5 seconds
        from("jpa:com.example.Outbox?consumer.query=select o from Outbox o where o.processed = false and o.status = 'Pending'")
                .routeId("outbox-event-route")
                .log("Processing Outbox event with ID: ${body.id}")

                // Dynamically determine Kafka topic from aggregate_type and event_type
                .process(exchange -> {
                    Outbox outbox = exchange.getIn().getBody(Outbox.class);
                    String topic = "events." + outbox.getAggregate_type().toLowerCase() + "." + outbox.getEvent_type().toLowerCase();

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
                    outbox.setSent_at(Instant.now());
                    exchange.getIn().setBody(outbox); // Update Outbox entity
                })

                // Save updated status back to the database
                .to("jpa:com.example.Outbox");
    }
}
