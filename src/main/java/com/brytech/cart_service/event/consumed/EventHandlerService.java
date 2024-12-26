package com.brytech.cart_service.event.consumed;

import com.brytech.cart_service.service.ProductCacheService;

import org.springframework.stereotype.Component;

// TODO: REMOVE IF NOT COMPLETELY IN USE

@Component
public class EventHandlerService {

    private final ProductCacheService productCacheService;
   
    public EventHandlerService(ProductCacheService productCacheService) {
        this.productCacheService = productCacheService;
    }

    public void handleProductCreatedEvent(ProductCreatedEvent event) {
        productCacheService.addProductToCache(event.productId(), event);
    }

    public void handleProductUpdatedEvent(ProductUpdatedEvent event) {
        productCacheService.updateProductInCache(event.productId(), event);
    }

    public void handleProductDeletedEvent(ProductDeletedEvent event) {
        productCacheService.removeProductFromCache(event.productId());
    }
}
