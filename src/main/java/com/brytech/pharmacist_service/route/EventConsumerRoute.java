package com.brytech.pharmacist_service.route;

import javax.sql.DataSource;

import com.brytech.pharmacist_service.event.consumed_events.PrescriptionCreatedEvent;
import com.brytech.pharmacist_service.exception.RequestValidationException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventConsumerRoute extends RouteBuilder {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure() throws Exception {
        from("kafka:prescriptionCreatedTopic")
            .unmarshal().json(JsonLibrary.Jackson, PrescriptionCreatedEvent.class)
            .routeId("prescription-created-consumer")
            .process(exchange -> {
                PrescriptionCreatedEvent event = exchange.getIn().getBody(PrescriptionCreatedEvent.class);

                // Validate event
                if (event.prescriptionId() == null || event.customerId() == null) {
                    throw new RequestValidationException("Invalid PrescriptionCreatedEvent recieved");
                }

                // Process event
                // reviewPrescription(event);
            })
        .to("log:prescriptionCreatedEventProcessed?level=INFO");
    }

    
}
