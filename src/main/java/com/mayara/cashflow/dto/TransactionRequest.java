package com.mayara.cashflow.dto;

import com.mayara.cashflow.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(

        @NotBlank(message = "A descrição é obrigatória.")
        @Size(max = 100, message = "A descrição deve possuir até 100 caracteres.")
        String description,

        @NotNull(message = "O valor é obrigatório.")
        @Positive(message = "O valor deve ser maior que zero.")
        BigDecimal amount,

        @NotNull(message = "A data é obrigatória.")
        LocalDate date,

        @NotNull(message = "O tipo de transação é obrigatório.")
        TransactionType type,

        Long categoryId
) {
}
