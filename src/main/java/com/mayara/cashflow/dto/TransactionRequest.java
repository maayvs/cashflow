package com.mayara.cashflow.dto;

import com.mayara.cashflow.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
        String description,
        BigDecimal amount,
        LocalDate date,
        TransactionType type
) {
}
