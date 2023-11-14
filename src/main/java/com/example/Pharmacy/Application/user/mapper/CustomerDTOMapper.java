package com.example.Pharmacy.Application.user.mapper;

import com.example.Pharmacy.Application.user.dto.CustomerDTO;
import com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CustomerDTOMapper implements Function<Customer, CustomerDTO> {


    @Override
    public CustomerDTO apply(Customer customer) {
        return new CustomerDTO(
                customer.getUserId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getMedicalHistory()
        );
    }
}
