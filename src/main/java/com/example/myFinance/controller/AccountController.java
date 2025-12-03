package com.example.myFinance.controller;

import com.example.myFinance.entity.Account;
import com.example.myFinance.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    // Create account for a user
    @PostMapping("/users/{userId}/accounts")
    public ResponseEntity<Account> createAccount(@PathVariable Long userId,
                                                 @RequestBody Account account) {
        Account created = accountService.createAccount(userId, account);
        return ResponseEntity.ok(created);
    }

    // Get all accounts for a user
    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<List<Account>> getAccountsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountsForUser(userId));
    }

    // Get active accounts for a user
    @GetMapping("/users/{userId}/accounts/active")
    public ResponseEntity<List<Account>> getActiveAccountsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getActiveAccountsForUser(userId));
    }

    // Get single account
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    // Update account
    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId,
                                                 @RequestBody Account account) {
        Account updated = accountService.updateAccount(accountId, account);
        return ResponseEntity.ok(updated);
    }

    // Delete account
    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
