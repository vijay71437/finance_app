package com.example.myFinance.controller;

import com.example.myFinance.entity.Transaction;
import com.example.myFinance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    // Create transaction for a user
    @PostMapping("/users/{userId}/transactions")
    public ResponseEntity<Transaction> createTransaction(@PathVariable Long userId,
                                                         @RequestBody Transaction transaction) {
        Transaction created = transactionService.createTransaction(userId, transaction);
        return ResponseEntity.ok(created);
    }

    // Get transaction by id
    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long transactionId) {
        return ResponseEntity.ok(transactionService.getTransactionById(transactionId));
    }

    // Update transaction
    @PutMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId,
                                                         @RequestBody Transaction transaction) {
        Transaction updated = transactionService.updateTransaction(transactionId, transaction);
        return ResponseEntity.ok(updated);
    }

    // Delete transaction
    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }

    // Get transactions for user in a date range
    @GetMapping("/users/{userId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsForUserInRange(
            @PathVariable Long userId,
            @RequestParam("from")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam("to")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        List<Transaction> transactions =
                transactionService.getTransactionsForUserInRange(userId, fromDate, toDate);

        return ResponseEntity.ok(transactions);
    }

    // Convenience: get transactions for a given month
    @GetMapping("/users/{userId}/transactions/monthly")
    public ResponseEntity<List<Transaction>> getTransactionsForUserInMonth(
            @PathVariable Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {

        List<Transaction> transactions =
                transactionService.getTransactionsForUserInMonth(userId, year, month);

        return ResponseEntity.ok(transactions);
    }
}
