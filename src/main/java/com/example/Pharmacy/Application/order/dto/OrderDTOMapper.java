package com.example.Pharmacy.Application.order.dto;

import com.example.Pharmacy.Application.order.dto.OrderDTO;
import com.example.Pharmacy.Application.order.model.Order;
import com.example.Pharmacy.Application.product.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class OrderDTOMapper implements Function<Order, OrderDTO> {

    @Override
    public OrderDTO apply(Order order) {
        return new OrderDTO(
                order.getOrderId(),
//                order.getOrderName(),
//                order.getDescription(),
                order.getStatus(),
                order.getQuantity(),
                order.getTotalPrice()
        );
    }
}
