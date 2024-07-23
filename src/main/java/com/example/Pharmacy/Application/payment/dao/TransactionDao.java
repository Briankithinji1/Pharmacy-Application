package main.java.com.example.Pharmacy.Application.payment.dao;

import main.java.com.example.Pharmacy.Application.payment.enums.TransactionStatus;
import main.java.com.example.Pharmacy.Application.payment.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionDao {

    List<Transaction> selectAllTransactions();
    Transaction saveTransaction(Transaction transaction);
    Optional<Transaction> findTransactionById(Long id);
    List<Transaction> findTransactionsByOrderId(Long orderId);
    List<Transaction> findTransactionsByPaymentId(Long paymentId);
    List<Transaction> findTransactionsByDateRange(LocalDate startDate, LocalDate endDate);
    void updateTransactionStatus(Long id, TransactionStatus status);
    void refundTransaction(Long id);

}
