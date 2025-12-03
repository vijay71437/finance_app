package com.example.myFinance.controller;



import com.example.myFinance.entity.Debt;
import com.example.myFinance.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DebtController {

    private final DebtService debtService;

    // Create debt for a user
    @PostMapping("/users/{userId}/debts")
    public ResponseEntity<Debt> createDebt(@PathVariable Long userId,
                                           @RequestBody Debt debt) {
        Debt created = debtService.createDebt(userId, debt);
        return ResponseEntity.ok(created);
    }

    // Get all debts for a user
    @GetMapping("/users/{userId}/debts")
    public ResponseEntity<List<Debt>> getDebtsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(debtService.getDebtsForUser(userId));
    }

    // Get single debt
    @GetMapping("/debts/{debtId}")
    public ResponseEntity<Debt> getDebt(@PathVariable Long debtId) {
        return ResponseEntity.ok(debtService.getDebtById(debtId));
    }

    // Update debt
    @PutMapping("/debts/{debtId}")
    public ResponseEntity<Debt> updateDebt(@PathVariable Long debtId,
                                           @RequestBody Debt debt) {
        Debt updated = debtService.updateDebt(debtId, debt);
        return ResponseEntity.ok(updated);
    }

    // Delete debt
    @DeleteMapping("/debts/{debtId}")
    public ResponseEntity<Void> deleteDebt(@PathVariable Long debtId) {
        debtService.deleteDebt(debtId);
        return ResponseEntity.noContent().build();
    }
}
