package com.mayara.cashflow.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table (name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    protected Transaction() {}

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

    public Long getId() {
        return id;
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
