package main.java.com.example.Pharmacy.Application.user.service;

import main.java.com.example.Pharmacy.Application.cart.enums.CartStatus;
import main.java.com.example.Pharmacy.Application.exception.DuplicateResourceException;
import main.java.com.example.Pharmacy.Application.exception.RequestValidationException;
import main.java.com.example.Pharmacy.Application.exception.ResourceNotFoundException;
import main.java.com.example.Pharmacy.Application.order.enums.OrderStatus;
import main.java.com.example.Pharmacy.Application.user.dao.CustomerDao;
import main.java.com.example.Pharmacy.Application.user.dto.CustomerDTO;
import main.java.com.example.Pharmacy.Application.user.mapper.CustomerDTOMapper;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import main.java.com.example.Pharmacy.Application.user.model.Pharmacist;
import main.java.com.example.Pharmacy.Application.user.model.Veterinarian;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerDTOMapper dtoMapper;

    public CustomerService(CustomerDao customerDao, CustomerDTOMapper dtoMapper) {
        this.customerDao = customerDao;
        this.dtoMapper = dtoMapper;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerDao.getAllCustomers()
                .stream()
                .map(dtoMapper)
                .toList();
    }

    public CustomerDTO getCustomerById(Long userId) {
        return customerDao.getCustomerByUserId(userId)
                .map(dtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id [%s] not found".formatted(userId)
                ));
    }

    public Optional<CustomerDTO> getCustomerByEmail(String email) {
        return Optional.ofNullable(customerDao.getCustomerByEmail(email)
                .map(dtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found"
                )));
    }

    public Optional<CustomerDTO> getCustomerByAssignedPharmacist(Pharmacist pharmacist) {
        return Optional.ofNullable(customerDao.getByAssignedPharmacist(pharmacist)
                .map(dtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found"
                )));
    }

    public Optional<CustomerDTO> getCustomerByAssignedVet(Veterinarian vet) {
        return Optional.ofNullable(customerDao.getByAssignedVet(vet)
                .map(dtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found"
                )));
    }

    public Optional<CustomerDTO> getCustomerByOrderList_Status(OrderStatus status) {
        return Optional.ofNullable(customerDao.getByOrderList_Status(status)
                .map(dtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found"
                )));
    }

    public Optional<CustomerDTO> getCustomerByCartList_Status(CartStatus status) {
        return Optional.ofNullable(customerDao.getByCartList_Status(status)
                .map(dtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found"
                )));
    }

    public void registerCustomer(Customer customer) {
        if (customerDao.isCustomerExistsById(customer.getUserId())) {
            throw new ResourceNotFoundException(
                    "Customer already exists");
        }
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomer(Long userId) {
        if (!customerDao.isCustomerExistsById(userId)) {
            throw new ResourceNotFoundException(
                    "Customer with id [%s] not found".formatted(userId)
            );
        }
        customerDao.deleteCustomer(userId);
    }

    @Transactional
    public void updateCustomer(Long userId, Customer customer) {

        Customer customer_s = customerDao.getCustomerByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with Id [%s] not found".formatted(userId)
                ));

        boolean changes = false;

        if (!Objects.equals(customer.getFirstname(), customer_s.getFirstname())) {
            customer_s.setFirstname(customer.getFirstname());
            changes = true;
        }

        if (!Objects.equals(customer.getLastname(), customer_s.getLastname())) {
            customer_s.setLastname(customer.getLastname());
            changes = true;
        }

        if (!Objects.equals(customer.getEmail(), customer_s.getEmail())) {
            if (customerDao.isCustomerExistsByEmail(customer.getEmail())) {
                throw new DuplicateResourceException(
                        "Email already taken"
                );
            }
            customer_s.setEmail(customer.getEmail());
            changes = true;
        }

        if (!Objects.equals(customer.getGender(), customer_s.getGender())) {
            customer_s.setGender(customer.getGender());
            changes = true;
        }

        if (!Objects.equals(customer.getAddress(), customer_s.getAddress())) {
            customer_s.setAddress(customer.getAddress());
            changes = true;
        }

        if (!Objects.equals(customer.getPhoneNumber(), customer_s.getPhoneNumber())) {
            customer_s.setPhoneNumber(customer.getPhoneNumber());
            changes = true;
        }

        if (!Objects.equals(customer.getMedicalHistory(), customer_s.getMedicalHistory())) {
            customer_s.setMedicalHistory(customer.getMedicalHistory());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException(
                    "No changes made"
            );
        }

        customerDao.updateCustomer(customer_s);
    }
}
