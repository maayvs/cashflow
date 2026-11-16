package com.mayara.cashflow.controller;

import com.mayara.cashflow.dto.TransactionRequest;
import com.mayara.cashflow.model.Transaction;
import com.mayara.cashflow.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> list() {
        return transactionService.findAll();
    }

    @GetMapping("/balance")
    public BigDecimal balance() {
        return transactionService.calculateBalance();
    }

    @GetMapping("/{id}")
    public Transaction findById(@PathVariable Long id) {
        return transactionService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        transactionService.delete(id);
    }

    @PutMapping("/{id}")
    public Transaction update(@PathVariable Long id, @RequestBody TransactionRequest request) {
        return transactionService.update(id, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction create(@RequestBody TransactionRequest request) {
        return transactionService.create(request);
    }
}
