package com.mayara.cashflow.dto;

import com.mayara.cashflow.model.Category;
import com.mayara.cashflow.model.Transaction;
import com.mayara.cashflow.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        String description,
        BigDecimal amount,
        LocalDate date,
        TransactionType type,
        Long categoryId,
        String categoryName
) {
    public static TransactionResponse from(Transaction transaction) {
        Category category = transaction.getCategory();
        return new TransactionResponse(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getType(),
                category != null ? category.getId() : null,
                category != null ? category.getName() : null
        );
    }
}
