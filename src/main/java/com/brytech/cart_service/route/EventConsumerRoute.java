package com.brytech.cart_service.route;

import com.brytech.cart_service.event.consumed.ProductCreatedEvent;
import com.brytech.cart_service.event.consumed.ProductDeletedEvent;
import com.brytech.cart_service.event.consumed.ProductUpdatedEvent;
import com.brytech.cart_service.exception.RequestValidationException;
import com.brytech.cart_service.service.ProductCacheService;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventConsumerRoute extends RouteBuilder {

    private final ProductCacheService productCacheService;

    public EventConsumerRoute(ProductCacheService productCacheService) {
        this.productCacheService = productCacheService;
    }

    @Value("${kafka.topic.product-created}")
    private String productCreatedTopic;

    @Value("${kafka.topic.product-updated}")
    private String productUpdatedTopic;

    @Value("${kafka.topic.product-deleted}")
    private String productDeletedTopic;

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

        from("kafka:" + productCreatedTopic)
            .unmarshal().json(JsonLibrary.Jackson, ProductCreatedEvent.class)
            .routeId("product-created-consumer")
            .process(exchange -> {
                ProductCreatedEvent event = exchange.getIn().getBody(ProductCreatedEvent.class);
                productCacheService.addProductToCache(event.productId(), event);
            })
            .log("Successfully processed ProductCreatedEvent with productId: ${body.productId}")
            .to("log:productCreatedEventProcessed?level=INFO");

        from("kafka:" + productUpdatedTopic)
            .unmarshal().json(JsonLibrary.Jackson, ProductUpdatedEvent.class)
            .routeId("product-updated-consumer")
            .process(exchange -> {
                ProductUpdatedEvent event = exchange.getIn().getBody(ProductUpdatedEvent.class);
                productCacheService.updateProductInCache(event.productId(), event);
            })
            .log("Successfully processed ProductUpdateEvent with productId: ${body.productId}")
            .to("log:productUpdatedEvent?level=INFO");


        from("kafka:" + productDeletedTopic)
            .unmarshal().json(JsonLibrary.Jackson, ProductDeletedEvent.class)
            .routeId("product-deleted-consumer")
            .process(exchange -> {
                ProductDeletedEvent event = exchange.getIn().getBody(ProductDeletedEvent.class);
                productCacheService.removeProductFromCache(event.productId());
            })
            .log("Successfully processed ProductDeletedEvent with productId: ${body.productId}")
            .to("log:productDeletedEventProcessed?level=INFO");
    } 
}
