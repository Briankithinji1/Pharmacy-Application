package com.example.Pharmacy.Application.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:emailProducer")
                .log("${body}")
                .to("kafka:emailTopic");

        from("kafka:emailTopic")
                .to("bean:emailService?method=sendHtmlMessage");
    }
}
