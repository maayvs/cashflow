package com.mayara.cashflow.service;

import com.mayara.cashflow.dto.TransactionRequest;
import com.mayara.cashflow.dto.TransactionResponse;
import com.mayara.cashflow.exception.ResourceNotFoundException;
import com.mayara.cashflow.model.Transaction;
import com.mayara.cashflow.model.TransactionType;
import com.mayara.cashflow.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionResponse> findAll() {
        List<TransactionResponse> responses = new ArrayList<>();
        for (Transaction transaction : transactionRepository.findAll()) {
            responses.add(TransactionResponse.from(transaction));
        }
        return responses;
    }

    public TransactionResponse create(TransactionRequest request) {
        Transaction transaction = new Transaction(
                request.description(),
                request.amount(),
                request.date(),
                request.type()
        );
        return TransactionResponse.from(transactionRepository.save(transaction));
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

    public TransactionResponse findById(Long id) {
        return TransactionResponse.from(findTransactionOrThrow(id));
    }

    public void delete(Long id) {
        Transaction transaction = findTransactionOrThrow(id);
        transactionRepository.delete(transaction);
    }

    public TransactionResponse update(Long id, TransactionRequest request) {
        Transaction transaction = findTransactionOrThrow(id);
        transaction.update(
                request.description(),
                request.amount(),
                request.date(),
                request.type()
        );
        return TransactionResponse.from(transactionRepository.save(transaction));
    }

    private Transaction findTransactionOrThrow(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada. id: " + id));
    }
}
