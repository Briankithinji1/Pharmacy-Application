package com.brytech.order_service.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.brytech.order_service.enumeration.PaymentStatus;
import com.brytech.order_service.model.PaymentDetails;
import com.brytech.order_service.repository.PaymentDetailsRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("paymentDetailsJpa")
public class PaymentDetailsJpaDataAccessService implements PaymentDetailsDao {

    private final PaymentDetailsRepository detailsRepository;

    public PaymentDetailsJpaDataAccessService(PaymentDetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @Override
    public PaymentDetails save(PaymentDetails details) {
       return detailsRepository.save(details);
    }

    @Override
    public Optional<PaymentDetails> findById(Long id) {
       return detailsRepository.findById(id);
    }

    @Override
    public List<PaymentDetails> findByPaymentStatus(PaymentStatus status) {
        return detailsRepository.findByPaymentStatus(status);
    }

    @Override
    public List<PaymentDetails> findByPaymentMethod(String paymentMethod) {
        return detailsRepository.findByPaymentMethod(paymentMethod);
    }

    @Override
    public List<PaymentDetails> findByPaymentDate(LocalDateTime paymentDate) {
        return detailsRepository.findByPaymentDate(paymentDate);
    }

    @Override
    public List<PaymentDetails> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return detailsRepository.findByPaymentDateBetween(startDate, endDate);
    }

    @Override
    public List<PaymentDetails> findByAmountGreaterThanEqual(BigDecimal amount) {
        return detailsRepository.findByAmountGreaterThanEqual(amount);
    }

    @Override
    public List<PaymentDetails> findByAmountLessThanEqual(BigDecimal amount) {
        return detailsRepository.findByAmountLessThanEqual(amount);
    }

    @Override
    public List<PaymentDetails> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount) {
        return detailsRepository.findByAmountBetween(minAmount, maxAmount);
    }

    @Override
    public void delete(Long id) {
        detailsRepository.deleteById(id);
    }

    @Override
    public Page<PaymentDetails> findAll(Pageable pageable) {
        return detailsRepository.findAll(pageable);
    }

    @Override
    public void saveAll(List<PaymentDetails> details) {
        detailsRepository.saveAll(details);
    }
}
