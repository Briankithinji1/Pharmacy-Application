package com.example.Pharmacy.Application.payment.dao;

import com.example.Pharmacy.Application.order.model.Order;
import com.example.Pharmacy.Application.payment.enums.PaymentType;
import com.example.Pharmacy.Application.payment.model.Payment;
import com.example.Pharmacy.Application.user.model.Customer;

import java.util.List;
import java.util.Optional;

public interface PaymentDao {
    List<Payment> selectAllPayments();
    Optional<Payment> findPaymentByCustomer(Customer customer);
    Optional<Payment> findPaymentByOrder(Order order);
    Optional<Payment> findPaymentById(Long paymentId);
    Optional<Payment> findPaymentsByType(PaymentType type);
    Payment createPayment(Payment payment);
    Payment updatePayment(Payment payment);
    void deletePayment(Payment payment);
    void deletePaymentById(Long paymentId);
    void processRefund(Payment payment); // ToDO: B2C
    void sendPaymentReceipt(Payment payment);
    void updatePaymentStatus(Payment payment ); // ToDO: Add PaymentStatus
}
