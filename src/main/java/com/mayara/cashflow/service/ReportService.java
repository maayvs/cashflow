package com.mayara.cashflow.service;

import com.mayara.cashflow.dto.MonthlySummaryResponse;
import com.mayara.cashflow.model.Transaction;
import com.mayara.cashflow.model.TransactionType;
import com.mayara.cashflow.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
@Transactional(readOnly = true)
public class ReportService {
    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public MonthlySummaryResponse getMonthlySummary(int year, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("O mês deve ser um número entre 1 e 12");
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction t : transactionRepository.findByDateBetween(start, end)) {
            if (t.getType() == TransactionType.INCOME) {
                totalIncome = totalIncome.add(t.getAmount());
            } else {
                totalExpense = totalExpense.add(t.getAmount());
            }
        }
        BigDecimal balance = totalIncome.subtract(totalExpense);
        return new MonthlySummaryResponse(year, month, totalIncome, totalExpense, balance);
    }
}
