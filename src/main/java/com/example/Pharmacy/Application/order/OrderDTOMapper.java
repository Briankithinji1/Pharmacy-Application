package com.example.Pharmacy.Application.order;

import com.example.Pharmacy.Application.product.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class OrderDTOMapper implements Function<OrderItem, OrderDTO> {

    @Override
    public OrderDTO apply(OrderItem orderItem) {
        List<ProductDTO> productDTOS = orderItem.getProducts()
                .stream()
                .map(product -> new ProductDTO(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getImageUri(),
//                        product.getCategory().getCategoryId(),
                        product.getCategory().getCategoryName(),
                        product.isAvailable()
                ))
                .toList();

        return new OrderDTO(
                orderItem.getOrderId(),
                orderItem.getOrderName(),
                orderItem.getDescription(),
                orderItem.getStatus(),
//                order.getProductPrice(),
                orderItem.getQuantity(),
                orderItem.getTotalPrice(),
                productDTOS
        );
    }
}
