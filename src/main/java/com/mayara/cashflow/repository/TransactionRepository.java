package com.mayara.cashflow.repository;

import com.mayara.cashflow.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
