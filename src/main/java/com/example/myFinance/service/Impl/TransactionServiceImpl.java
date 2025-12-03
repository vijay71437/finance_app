package com.example.myFinance.service.Impl;


import com.example.myFinance.entity.Account;
import com.example.myFinance.entity.Category;
import com.example.myFinance.entity.Transaction;
import com.example.myFinance.entity.User;
import com.example.myFinance.exception.ResourceNotFoundException;
import com.example.myFinance.repository.AccountRepository;
import com.example.myFinance.repository.CategoryRepository;
import com.example.myFinance.repository.TransactionRepository;
import com.example.myFinance.repository.UserRepository;
import com.example.myFinance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Transaction createTransaction(Long userId, Transaction transaction) {
        User user = getUserEntity(userId);

        Long accountId = transaction.getAccount() != null ? transaction.getAccount().getId() : null;
        Long categoryId = transaction.getCategory() != null ? transaction.getCategory().getId() : null;

        if (accountId == null || categoryId == null) {
            throw new IllegalArgumentException("accountId and categoryId must be provided inside Transaction");
        }

        Account account = getAccountEntity(accountId);
        Category category = getCategoryEntity(categoryId);

        // Optional: Check ownership
        if (!account.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Account does not belong to the given user");
        }
        if (!category.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Category does not belong to the given user");
        }

        transaction.setId(null);
        transaction.setUser(user);
        transaction.setAccount(account);
        transaction.setCategory(category);

        if (transaction.getDateTime() == null) {
            transaction.setDateTime(LocalDateTime.now());
        }

        // Optional: adjust account balance based on transaction type here

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction updateTransaction(Long transactionId, Transaction updatedTransaction) {
        Transaction existing = getTransactionEntity(transactionId);

        // If account or category IDs are passed in updatedTransaction, update them
        if (updatedTransaction.getAccount() != null && updatedTransaction.getAccount().getId() != null) {
            Account newAccount = getAccountEntity(updatedTransaction.getAccount().getId());
            existing.setAccount(newAccount);
        }

        if (updatedTransaction.getCategory() != null && updatedTransaction.getCategory().getId() != null) {
            Category newCategory = getCategoryEntity(updatedTransaction.getCategory().getId());
            existing.setCategory(newCategory);
        }

        if (updatedTransaction.getType() != null) {
            existing.setType(updatedTransaction.getType());
        }

        if (updatedTransaction.getAmount() != null) {
            existing.setAmount(updatedTransaction.getAmount());
        }

        if (updatedTransaction.getDateTime() != null) {
            existing.setDateTime(updatedTransaction.getDateTime());
        }

        existing.setDescription(updatedTransaction.getDescription());

        // Optional: recompute account balance difference here

        return transactionRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteTransaction(Long transactionId) {
        Transaction existing = getTransactionEntity(transactionId);
        // Optional: rollback balance on account
        transactionRepository.delete(existing);
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return getTransactionEntity(transactionId);
    }

    @Override
    public List<Transaction> getTransactionsForUserInRange(Long userId,
                                                           LocalDate fromDate,
                                                           LocalDate toDate) {
        User user = getUserEntity(userId);

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);

        return transactionRepository.findByUserAndDateTimeBetween(user, fromDateTime, toDateTime);
    }

    @Override
    public List<Transaction> getTransactionsForUserInMonth(Long userId,
                                                           int year,
                                                           int month) {
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to = from.plusMonths(1).minusDays(1);
        return getTransactionsForUserInRange(userId, from, to);
    }

    // ---------- Helpers ----------

    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private Account getAccountEntity(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
    }

    private Category getCategoryEntity(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
    }

    private Transaction getTransactionEntity(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));
    }
}

