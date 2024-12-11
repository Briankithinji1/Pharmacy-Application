package com.brytech.order_service.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.PaymentStatus;
import com.brytech.order_service.model.PaymentDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentDetailsDao {

    PaymentDetails save(PaymentDetails details);
    Optional<PaymentDetails> findById(Long id);
    List<PaymentDetails> findByPaymentStatus(PaymentStatus status);
    List<PaymentDetails> findByPaymentMethod(String paymentMethod);
    List<PaymentDetails> findByPaymentDate(LocalDateTime paymentDate);

    List<PaymentDetails> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<PaymentDetails> findByAmountGreaterThanEqual(BigDecimal amount);
    List<PaymentDetails> findByAmountLessThanEqual(BigDecimal amount);
    List<PaymentDetails> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
//    List<PaymentDetails> findByCustomerId(Long customerId)

    void delete(Long id);
    Page<PaymentDetails> findAll(Pageable pageable);
    void saveAll(List<PaymentDetails> details);
}
