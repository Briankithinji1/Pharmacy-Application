package com.brytech.customer_service.service;

import com.brytech.customer_service.dao.CustomerDAO;
import com.brytech.customer_service.dto.CustomerDTO;
import com.brytech.customer_service.dto.OrderReferenceDTO;
import com.brytech.customer_service.dto.PrescriptionReferenceDTO;
import com.brytech.customer_service.events.request.OrderHistoryRequestEvent;
import com.brytech.customer_service.events.request.OrderStatusRequestEvent;
import com.brytech.customer_service.events.request.PrescriptionHistoryRequestEvent;
import com.brytech.customer_service.events.request.PrescriptionStatusRequestEvent;
import com.brytech.customer_service.exceptions.ResourceNotFoundException;
import com.brytech.customer_service.model.Customer;
import com.brytech.customer_service.model.Outbox;
import com.brytech.customer_service.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final OutboxRepository outboxRepository;
    private final ModelMapper mapper;
    private final EventService eventService;

    public CustomerDTO getCustomerById(UUID id) {
        return customerDAO.getCustomerById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with ID [%s] not found".formatted(id)
                ));
    }

    public CustomerDTO getCustomerByEmail(String email) {
        return customerDAO.getCustomerByEmail(email)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with email [%s] not found".formatted(email)
                ));
    }

    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        return customerDAO.getAllCustomers(pageable)
                .map(this::convertToDto);
    }

    public Page<CustomerDTO> getCustomerByStatus(String status, Pageable pageable) {
        return customerDAO.getCustomersByStatus(status, pageable)
                .map(this::convertToDto);
    }

    public CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerDAO.getCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with ID [%s] not found".formatted(id)
                ));

        existingCustomer.setFirst_name(customerDTO.first_name());
        existingCustomer.setLast_name(customerDTO.last_name());
        existingCustomer.setEmail(customerDTO.email());
        existingCustomer.setPhone_number(customerDTO.phone_number());
        existingCustomer.setAddress(customerDTO.address());
        existingCustomer.setAccount_status(customerDTO.account_status());

        Customer updatedCustomer = customerDAO.updateCustomer(existingCustomer);

        return convertToDto(updatedCustomer);
    }

    public void deleteCustomer(UUID id) {
        try {
            customerDAO.deleteCustomer(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "Customer with ID [%s] not found".formatted(id)
            );
        }
    }

    private CustomerDTO convertToDto(Customer customer) {
        return mapper.map(customer, CustomerDTO.class);
    }


    // PRESCRIPTION RELATED

    public void createPrescriptionUploadEvent(PrescriptionReferenceDTO prescriptionReference) {
        Outbox outboxEvent = new Outbox();
        outboxEvent.setEvent_type("PRESCRIPTION_UPLOAD");
        outboxEvent.setAggregate_type("PRESCRIPTION");
        outboxEvent.setPayload(eventService.convertToJSON(prescriptionReference));
        outboxEvent.setStatus("PENDING");
        outboxEvent.setCreated_at(Instant.now());
        outboxRepository.save(outboxEvent);
    }

    public void requestPrescriptionHistory(UUID customerId) {
        PrescriptionHistoryRequestEvent event = new PrescriptionHistoryRequestEvent();
        event.setCustomerId(customerId);

        Outbox outboxEvent = new Outbox();
        outboxEvent.setEvent_type("PRESCRIPTION_HISTORY_REQUEST");
        outboxEvent.setAggregate_type("PRESCRIPTION");
        outboxEvent.setPayload(eventService.convertToJSON(event));
        outboxEvent.setStatus("PENDING");
        outboxEvent.setCreated_at(Instant.now());

        outboxRepository.save(outboxEvent);
    }

    public void requestPrescriptionStatus(UUID prescriptionId) {
        PrescriptionStatusRequestEvent event = new PrescriptionStatusRequestEvent();
        event.setPrescriptionId(prescriptionId);

        Outbox outboxEvent = new Outbox();
        outboxEvent.setEvent_type("PRESCRIPTION_STATUS_REQUEST");
        outboxEvent.setAggregate_type("PRESCRIPTION");
        outboxEvent.setPayload(eventService.convertToJSON(event));
        outboxEvent.setStatus("PENDING");
        outboxEvent.setCreated_at(Instant.now());

        outboxRepository.save(outboxEvent);
    }

    // ORDER RELATED

    public void createOrderEvent(OrderReferenceDTO orderReferenceDTO) {
        Outbox outboxEvent = new Outbox();
        outboxEvent.setEvent_type("ORDER_CREATION");
        outboxEvent.setAggregate_type("ORDER");
        outboxEvent.setPayload(eventService.convertToJSON(orderReferenceDTO));
        outboxEvent.setStatus("PENDING");
        outboxEvent.setCreated_at(Instant.now());

        outboxRepository.save(outboxEvent);
    }

    public void requestOrderHistory(UUID customerId) {
        OrderHistoryRequestEvent event = new OrderHistoryRequestEvent();
        event.setCustomerId(customerId);

        Outbox outboxEvent = new Outbox();
        outboxEvent.setEvent_type("ORDER_HISTORY_REQUEST");
        outboxEvent.setAggregate_type("ORDER");
        outboxEvent.setPayload(eventService.convertToJSON(event));
        outboxEvent.setStatus("PENDING");
        outboxEvent.setCreated_at(Instant.now());

        outboxRepository.save(outboxEvent);
    }

    public void requestOrderStatus(UUID orderId) {
        OrderStatusRequestEvent event = new OrderStatusRequestEvent();
        event.setOrderId(orderId);

        Outbox outboxEvent = new Outbox();
        outboxEvent.setEvent_type("ORDER_STATUS_REQUEST");
        outboxEvent.setAggregate_type("ORDER");
        outboxEvent.setPayload(eventService.convertToJSON(event));
        outboxEvent.setStatus("PENDING");
        outboxEvent.setCreated_at(Instant.now());

        outboxRepository.save(outboxEvent);
    }
}
