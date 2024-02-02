package com.example.Pharmacy.Application.payment.dto;

import com.example.Pharmacy.Application.order.dto.OrderDTO;
import com.example.Pharmacy.Application.payment.enums.PaymentType;
import com.example.Pharmacy.Application.user.dto.CustomerDTO;

import java.math.BigDecimal;

public record PaymentDTO(
        Long paymentId,
        BigDecimal amount,
        PaymentType paymentType,
        OrderDTO order,
        CustomerDTO customer
) { }
