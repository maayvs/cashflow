package com.mayara.cashflow.service;

import com.mayara.cashflow.dto.CategoryTotalResponse;
import com.mayara.cashflow.dto.MonthlySummaryResponse;
import com.mayara.cashflow.model.Transaction;
import com.mayara.cashflow.model.TransactionType;
import com.mayara.cashflow.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReportService {
    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public MonthlySummaryResponse getMonthlySummary(int year, int month) {
        YearMonth yearMonth = toYearMonth(year, month);
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

    private YearMonth toYearMonth(int year, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("O mês deve estar entre 1 e 12.");
        }
        return YearMonth.of(year, month);
    }

    public List<CategoryTotalResponse> getExpenseByCategory(int year, int month) {
        YearMonth yearMonth = toYearMonth(year, month);
        return transactionRepository.sumByCategory(
                TransactionType.EXPENSE,
                yearMonth.atDay(1),
                yearMonth.atEndOfMonth()
        );
    }

    public List<CategoryTotalResponse> getIncomeByCategory(int year, int month) {
        YearMonth yearMonth = toYearMonth(year, month);
        return transactionRepository.sumByCategory(
                TransactionType.INCOME,
                yearMonth.atDay(1),
                yearMonth.atEndOfMonth()
        );
    }
}
