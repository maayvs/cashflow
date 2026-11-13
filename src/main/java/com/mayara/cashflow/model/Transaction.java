package com.mayara.cashflow.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private TransactionType type;

    public Transaction(String description, BigDecimal amount, LocalDate date, TransactionType type) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória.");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
        if (type == null) {
            throw new IllegalArgumentException("O tipo da transação é obrigatório.");
        }

        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }
}
