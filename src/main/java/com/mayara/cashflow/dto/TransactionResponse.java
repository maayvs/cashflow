package com.mayara.cashflow.dto;

import com.mayara.cashflow.model.Transaction;
import com.mayara.cashflow.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        String description,
        BigDecimal amount,
        LocalDate date,
        TransactionType type
) {
    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getType()
        );
    }
}
