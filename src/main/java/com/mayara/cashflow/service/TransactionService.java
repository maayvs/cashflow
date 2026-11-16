package com.mayara.cashflow.service;

import com.mayara.cashflow.dto.TransactionRequest;
import com.mayara.cashflow.exception.ResourceNotFoundException;
import com.mayara.cashflow.model.Transaction;
import com.mayara.cashflow.model.TransactionType;
import com.mayara.cashflow.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    //TEMPORÁRIO (Necessário ainda configurar o banco de dados
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction create(TransactionRequest request) {
        Transaction transaction = new Transaction(
                request.description(),
                request.amount(),
                request.date(),
                request.type()
        );
        transactionRepository.save(transaction);
        return transaction;
    }

    public BigDecimal calculateBalance() {
        BigDecimal result = BigDecimal.ZERO;
        for (Transaction t : transactionRepository.findAll()) {
            if (t.getType() == TransactionType.INCOME) {
                result = result.add(t.getAmount());
            } else {
                result = result.subtract(t.getAmount());
            }
        }
        return result;
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada. id: " + id));
    }

    public void delete(Long id) {
        Transaction transaction = findById(id);
        transactionRepository.delete(transaction);
    }
}
