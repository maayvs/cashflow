package com.mayara.cashflow.controller;

import com.mayara.cashflow.model.Transaction;
import com.mayara.cashflow.model.TransactionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    //TEMPORÁRIO (Necessário ainda configurar o banco de dados
    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionController() {
        transactions.add(new Transaction("Mercado", new BigDecimal("250.00"), LocalDate.of(2026, 11, 10), TransactionType.EXPENSE));
        transactions.add(new Transaction("Salário", new BigDecimal("4500.00"), LocalDate.of(2026, 11, 5), TransactionType.INCOME));
        transactions.add(new Transaction("Cartão", new BigDecimal("500.00"), LocalDate.of(2026, 11, 10), TransactionType.EXPENSE));
    }

    @GetMapping
    public List<Transaction> list() {
        return transactions;
    }

    @GetMapping("/balance")
    public BigDecimal balance() {
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
