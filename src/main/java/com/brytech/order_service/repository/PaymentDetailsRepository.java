package com.brytech.order_service.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.brytech.order_service.enumeration.PaymentStatus;
import com.brytech.order_service.model.PaymentDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long>{

    List<PaymentDetails> findByPaymentStatus(PaymentStatus status);
    List<PaymentDetails> findByPaymentMethod(String paymentMethod);
    List<PaymentDetails> findByPaymentDate(LocalDateTime paymentDate);
    List<PaymentDetails> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<PaymentDetails> findByAmountGreaterThanEqual(BigDecimal amount);
    List<PaymentDetails> findByAmountLessThanEqual(BigDecimal amount);
    List<PaymentDetails> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
}
