package main.java.com.example.Pharmacy.Application.payment.repository;

import main.java.com.example.Pharmacy.Application.payment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
