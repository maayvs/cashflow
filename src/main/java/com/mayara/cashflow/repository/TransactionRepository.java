package com.mayara.cashflow.repository;

import com.mayara.cashflow.dto.CategoryTotalResponse;
import com.mayara.cashflow.model.Transaction;
import com.mayara.cashflow.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByDateBetween(LocalDate start, LocalDate end);

    @Query("""
            SELECT new com.mayara.cashflow.dto.CategoryTotalResponse(
                COALESCE(c.name, 'Sem categoria'),
                SUM(t.amount)
            )
            FROM Transaction t
            LEFT JOIN t.category c
            WHERE t.type = :type
              AND t.date BETWEEN :start AND :end
            GROUP BY c.name
            ORDER BY SUM(t.amount) DESC
            """)
    List<CategoryTotalResponse> sumByCategory(@Param("type") TransactionType type,
                                              @Param("start") LocalDate start,
                                              @Param("end") LocalDate end);
}
