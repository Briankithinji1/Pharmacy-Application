package com.example.Pharmacy.Application.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:emailProducer")
                .log("${body}")
                .to("kafka:emailTopic?brokers=localhost:9092");

        from("kafka:emailTopic?brokers=localhost:9092")
                .to("bean:emailService?method=sendHtmlMessage");
    }
}
