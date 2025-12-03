package com.example.myFinance.service;



import com.example.myFinance.entity.Account;

import java.util.List;

public interface AccountService {

    Account createAccount(Long userId, Account account);

    Account updateAccount(Long accountId, Account updatedAccount);

    void deleteAccount(Long accountId);

    Account getAccountById(Long accountId);

    List<Account> getAccountsForUser(Long userId);

    List<Account> getActiveAccountsForUser(Long userId);
}

