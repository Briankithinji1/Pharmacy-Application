package main.java.com.example.Pharmacy.Application.payment.dto;

import main.java.com.example.Pharmacy.Application.order.dto.OrderDTO;
import main.java.com.example.Pharmacy.Application.payment.enums.PaymentType;
import main.java.com.example.Pharmacy.Application.user.dto.CustomerDTO;

import java.math.BigDecimal;

public record PaymentDTO(
        Long paymentId,
        BigDecimal amount,
        PaymentType paymentType,
        OrderDTO order,
        CustomerDTO customer
) { }
