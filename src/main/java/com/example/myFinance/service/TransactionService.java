package com.example.myFinance.service;


import com.example.myFinance.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Long userId, Transaction transaction);

    Transaction updateTransaction(Long transactionId, Transaction updatedTransaction);

    void deleteTransaction(Long transactionId);

    Transaction getTransactionById(Long transactionId);

    List<Transaction> getTransactionsForUserInRange(Long userId,
                                                    LocalDate fromDate,
                                                    LocalDate toDate);

    List<Transaction> getTransactionsForUserInMonth(Long userId,
                                                    int year,
                                                    int month);
}

