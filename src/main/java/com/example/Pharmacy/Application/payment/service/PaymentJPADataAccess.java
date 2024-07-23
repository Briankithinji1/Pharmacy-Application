package main.java.com.example.Pharmacy.Application.payment.service;

import main.java.com.example.Pharmacy.Application.order.model.Order;
import main.java.com.example.Pharmacy.Application.payment.dao.PaymentDao;
import main.java.com.example.Pharmacy.Application.payment.enums.PaymentType;
import main.java.com.example.Pharmacy.Application.payment.model.Payment;
import main.java.com.example.Pharmacy.Application.payment.repository.PaymentRepository;
import main.java.com.example.Pharmacy.Application.user.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("paymentJPA")
public class PaymentJPADataAccess implements PaymentDao {

    private final PaymentRepository paymentRepository;

    public PaymentJPADataAccess(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> selectAllPayments() {
        return null;
    }

    @Override
    public Optional<Payment> findPaymentByCustomer(Customer customer) {
        return Optional.empty();
    }

    @Override
    public Optional<Payment> findPaymentByOrder(Order order) {
        return Optional.empty();
    }

    @Override
    public Optional<Payment> findPaymentById(Long paymentId) {
        return Optional.empty();
    }

    @Override
    public Optional<Payment> findPaymentsByType(PaymentType type) {
        return Optional.empty();
    }

    @Override
    public Payment createPayment(Payment payment) {
        return null;
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return null;
    }

    @Override
    public void deletePayment(Payment payment) {

    }

    @Override
    public void deletePaymentById(Long paymentId) {

    }

    @Override
    public void processRefund(Payment payment) {

    }

    @Override
    public void sendPaymentReceipt(Payment payment) {

    }

    @Override
    public void updatePaymentStatus(Payment payment) {

    }
}
