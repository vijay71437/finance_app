package com.example.myFinance.service.Impl;


import com.example.myFinance.entity.Account;
import com.example.myFinance.entity.User;
import com.example.myFinance.exception.ResourceNotFoundException;
import com.example.myFinance.repository.AccountRepository;
import com.example.myFinance.repository.UserRepository;
import com.example.myFinance.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public Account createAccount(Long userId, Account account) {
        User user = getUserEntity(userId);
        account.setId(null);
        account.setUser(user);

        if (account.getInitialBalance() == null) {
            account.setInitialBalance(BigDecimal.ZERO);
        }
        if (account.getCurrentBalance() == null) {
            account.setCurrentBalance(account.getInitialBalance());
        }

        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Long accountId, Account updatedAccount) {
        Account existing = getAccountEntity(accountId);

        existing.setName(updatedAccount.getName());
        existing.setType(updatedAccount.getType());
        existing.setInitialBalance(updatedAccount.getInitialBalance());
        existing.setCurrentBalance(updatedAccount.getCurrentBalance());
        existing.setCurrency(updatedAccount.getCurrency());
        existing.setActive(updatedAccount.isActive());

        return accountRepository.save(existing);
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account existing = getAccountEntity(accountId);
        accountRepository.delete(existing);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return getAccountEntity(accountId);
    }

    @Override
    public List<Account> getAccountsForUser(Long userId) {
        User user = getUserEntity(userId);
        return accountRepository.findByUser(user);
    }

    @Override
    public List<Account> getActiveAccountsForUser(Long userId) {
        User user = getUserEntity(userId);
        return accountRepository.findByUserAndActiveTrue(user);
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
}

