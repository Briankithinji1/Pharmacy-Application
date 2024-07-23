package main.java.com.example.Pharmacy.Application.user.controller;

import lombok.RequiredArgsConstructor;
import main.java.com.example.Pharmacy.Application.cart.enums.CartStatus;
import main.java.com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import main.java.com.example.Pharmacy.Application.order.enums.OrderStatus;
import main.java.com.example.Pharmacy.Application.user.dto.CustomerDTO;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import main.java.com.example.Pharmacy.Application.user.model.Pharmacist;
import main.java.com.example.Pharmacy.Application.user.repository.PharmacistRepository;
import main.java.com.example.Pharmacy.Application.user.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final PharmacistRepository repository;

    @GetMapping("all")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("{userId}")
    public CustomerDTO getCustomerByUserId(
            @PathVariable("userId") Long userId
    ) {
        return customerService.getCustomerById(userId);
    }

    @GetMapping("{email}")
    public Optional<CustomerDTO> getCustomerByEmail(
            @PathVariable("email") String email
    ) {
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("byAssignedPharmacist")
    public Optional<CustomerDTO> getCustomerByAssignedPharmacist(@RequestParam Long userId) {
        Pharmacist pharmacist = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pharmacist not found with Id: " + userId
                ));
        return customerService.getCustomerByAssignedPharmacist(pharmacist);
    }

    // ToDo: Use vet repository
//    @GetMapping("byAssignedVet")
//    public Optional<CustomerDTO> getCustomerByAssignedVeterinarian(
//            @RequestParam Long userId
//    ) {
//        Veterinarian veterinarian = repository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "Veterinarian not found with ID: " + userId
//                ));
//        return customerService.getCustomerByAssignedVet(veterinarian);
//    }

    @GetMapping("byOrderListStatus")
    public ResponseEntity<CustomerDTO> getCustomerByOrderListStatus(@RequestParam OrderStatus status) {
        Optional<CustomerDTO> customer = customerService.getCustomerByOrderList_Status(status);

        return customer
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("byCartListStatus")
    public ResponseEntity<CustomerDTO> getCustomerByCartListStatus(@RequestParam CartStatus status) {
        Optional<CustomerDTO> customer = customerService.getCustomerByCartList_Status(status);

        return customer
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("addCustomer")
    public ResponseEntity<?> registerCustomer(
            @RequestBody Customer customer
    ) {
        customerService.registerCustomer(customer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{userId}")
    public void deleteCustomer(
            @PathVariable("userId") Long userId
    ) {
        customerService.deleteCustomer(userId);
    }

    @PutMapping("update/{userId}")
    public void updateCustomer(
            @PathVariable("userId") Long userId,
            @RequestBody Customer customer
    ) {
        customerService.updateCustomer(userId, customer);
    }

}
