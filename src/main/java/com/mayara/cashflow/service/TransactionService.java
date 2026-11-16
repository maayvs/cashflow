package com.mayara.cashflow.service;

import com.mayara.cashflow.dto.TransactionRequest;
import com.mayara.cashflow.model.Transaction;
import com.mayara.cashflow.model.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    //TEMPORÁRIO (Necessário ainda configurar o banco de dados
    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionService() {
        transactions.add(new Transaction("Mercado", new BigDecimal("250.00"), LocalDate.of(2026, 11, 10), TransactionType.EXPENSE));
        transactions.add(new Transaction("Salário", new BigDecimal("4500.00"), LocalDate.of(2026, 11, 5), TransactionType.INCOME));
        transactions.add(new Transaction("Cartão", new BigDecimal("500.00"), LocalDate.of(2026, 11, 10), TransactionType.EXPENSE));
    }

    public List<Transaction> findAll() {
        return transactions;
    }

    public Transaction create(TransactionRequest request) {
        Transaction transaction = new Transaction(
                request.description(),
                request.amount(),
                request.date(),
                request.type()
        );
        transactions.add(transaction);
        return transaction;
    }

    public BigDecimal calculateBalance() {
        BigDecimal result = BigDecimal.ZERO;
        for (Transaction t : transactions) {
            if (t.getType() == TransactionType.INCOME) {
                result = result.add(t.getAmount());
            } else {
                result = result.subtract(t.getAmount());
            }
        }
        return result;
    }

}
