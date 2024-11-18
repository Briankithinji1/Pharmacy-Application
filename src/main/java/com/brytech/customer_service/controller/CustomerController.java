package com.brytech.customer_service.controller;

import com.brytech.customer_service.dto.CustomerDTO;
import com.brytech.customer_service.events.response.OrderHistoryResponseEvent;
import com.brytech.customer_service.events.response.OrderStatusResponseEvent;
import com.brytech.customer_service.events.response.PrescriptionHistoryResponseEvent;
import com.brytech.customer_service.events.response.PrescriptionStatusResponseEvent;
import com.brytech.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger getLog = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(
            @PathVariable("id")UUID id
    ) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{email}")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(
            @PathVariable("email") String email
    ) {
        CustomerDTO customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(Pageable pageable) {
        Page<CustomerDTO> customers = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/status")
    public ResponseEntity<Page<CustomerDTO>> getCustomerByStatus(
            @RequestParam String status, Pageable pageable
    ) {
        Page<CustomerDTO> customerByStatus = customerService.getCustomerByStatus(status, pageable);
        return ResponseEntity.ok(customerByStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable("id") UUID id,
            @RequestBody CustomerDTO customerDTO
    ) {
        CustomerDTO updateCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updateCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(
            @PathVariable("id") UUID id
    ) {
        customerService.deleteCustomer(id);
    }


    // For kafka listeners to prescription history and status

    @KafkaListener(topics = "events.prescription.prescription_history_response")
    public void handlePrescriptionHistoryResponse(PrescriptionHistoryResponseEvent event) {
        // Cache or save the retrieved prescription history for quick access
    }

    @KafkaListener(topics = "events.prescription.prescription_status_response")
    public void handlePrescriptionStatusResponse(PrescriptionStatusResponseEvent event) {
        // Cache or update the status of the prescription
    }


    // For kafka listeners to order history and status

    @KafkaListener(topics = "events.order.order_history_response")
    public void handleOrderHistoryResponse(OrderHistoryResponseEvent event) {
        // Cache or store the retrieved order history
    }

    @KafkaListener(topics = "events.order.order_status_response")
    public void handleOrderStatusResponse(OrderStatusResponseEvent event) {
        // Update or cache the order status for quick access by the customer
    }
}
