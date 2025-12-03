package com.example.myFinance.controller;

import com.example.myFinance.entity.Budget;
import com.example.myFinance.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BudgetController {

    private final BudgetService budgetService;

    // Create or update budget for user (for a given category & month)
    @PostMapping("/users/{userId}/budgets")
    public ResponseEntity<Budget> createOrUpdateBudget(@PathVariable Long userId,
                                                       @RequestBody Budget budget) {
        Budget saved = budgetService.createOrUpdateBudget(userId, budget);
        return ResponseEntity.ok(saved);
    }

    // Get budget by id
    @GetMapping("/budgets/{budgetId}")
    public ResponseEntity<Budget> getBudget(@PathVariable Long budgetId) {
        return ResponseEntity.ok(budgetService.getBudgetById(budgetId));
    }

    // Delete budget
    @DeleteMapping("/budgets/{budgetId}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long budgetId) {
        budgetService.deleteBudget(budgetId);
        return ResponseEntity.noContent().build();
    }

    // Get all budgets for a user in a month
    @GetMapping("/users/{userId}/budgets")
    public ResponseEntity<List<Budget>> getBudgetsForUserAndMonth(
            @PathVariable Long userId,
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month) {

        List<Budget> budgets =
                budgetService.getBudgetsForUserAndMonth(userId, year, month);

        return ResponseEntity.ok(budgets);
    }

    // Get a specific budget for user + category + month
    @GetMapping("/users/{userId}/budgets/category/{categoryId}")
    public ResponseEntity<Budget> getBudgetForUserCategoryAndMonth(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month) {

        Optional<Budget> budgetOpt =
                budgetService.getBudgetForUserCategoryAndMonth(userId, categoryId, year, month);

        return budgetOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
