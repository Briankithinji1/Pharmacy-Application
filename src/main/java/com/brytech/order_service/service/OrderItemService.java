package com.brytech.order_service.service;

import java.util.List;

import com.brytech.order_service.dao.OrderItemDao;
import com.brytech.order_service.dto.OrderItemDTO;
import com.brytech.order_service.exception.RequestValidationException;
import com.brytech.order_service.exception.ResourceNotFoundException;
import com.brytech.order_service.model.OrderItem;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemDao itemDao;
    private final ModelMapper mapper;

    public OrderItemDTO addOrderItem(OrderItemDTO itemDTO) {
        if (itemDTO == null) {
            throw new RequestValidationException("Order item must not be null");
        }

        OrderItem orderItem = convertToEntity(itemDTO);

        OrderItem savedItem = itemDao.save(orderItem);

        return convertToDto(savedItem);
    }

    public OrderItemDTO findItemsById(Long itemId) {
        return itemDao.findById(itemId)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Item with ID [%s] not found".formatted(itemId)
            ));
    }

    public List<OrderItemDTO> findByOrderId(Long orderId) {
        if (orderId == null) {
            throw new RequestValidationException("Order ID must not be null");
        }

        return itemDao.findByOrderId(orderId)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public List<OrderItemDTO> findByProductId(Long productId) {
        if (productId == null) {
            throw new RequestValidationException("Product ID must not be null");
        }

        return itemDao.findByProductId(productId)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public List<OrderItemDTO> findByProductName(String productName) {
        if (productName == null) {
            throw new RequestValidationException("Product name must not be null");
        }

        return itemDao.findByProductName(productName)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public List<OrderItemDTO> findByOrderIdProductId(Long orderId, Long productId) {
        if (orderId == null || productId == null) {
            throw new RequestValidationException("Order and Product ID must not be null");
        }

        return itemDao.findByOrderIdAndProductId(orderId, productId)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    public Page<OrderItemDTO> findAllItems(Pageable pageable) {
        return itemDao.findAll(pageable)
            .map(this::convertToDto);
    }

    public List<OrderItemDTO> saveAll(List<OrderItemDTO> itemDTOs) {
       List<OrderItem> items = itemDTOs.stream()
           .map(this::convertToEntity)
           .toList();

       List<OrderItem> savedItems = itemDao.saveAll(items);
       return savedItems.stream()
           .map(this::convertToDto)
           .toList();
    }

    public void deleteItem(Long itemId) {
        try {
            itemDao.deleteItem(itemId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Item with ID [%s] not found".formatted(itemId)
            );
        }
    }
    
    public OrderItemDTO updateOrderItem(Long itemId, OrderItemDTO updatedItemDTO) {
        if (itemId == null || updatedItemDTO == null) {
            throw new RequestValidationException("Item ID and updated item data must not be null");
        }

        OrderItem existingItem = itemDao.findById(itemId)
            .orElseThrow(() -> new ResourceNotFoundException(
                        "Item with ID [%s] not found".formatted(itemId)
            ));

        existingItem.setQuantity(updatedItemDTO.quantity());
        existingItem.setPrice(updatedItemDTO.price());
        existingItem.setProductId(updatedItemDTO.productId());
        existingItem.setProductName(updatedItemDTO.productName());
        existingItem.setTotal(updatedItemDTO.total());
        
        OrderItem savedItem = itemDao.save(existingItem);

        return convertToDto(savedItem);
    }

    private OrderItemDTO convertToDto(OrderItem item) {
        return mapper.map(item, OrderItemDTO.class);
    }

    private OrderItem convertToEntity(OrderItemDTO itemDTO) {
        return mapper.map(itemDTO, OrderItem.class);
    }
}
