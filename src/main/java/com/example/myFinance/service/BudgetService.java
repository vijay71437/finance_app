package com.example.myFinance.service;



import com.example.myFinance.entity.Budget;

import java.util.List;
import java.util.Optional;

public interface BudgetService {

    Budget createOrUpdateBudget(Long userId, Budget budget);

    Budget getBudgetById(Long budgetId);

    void deleteBudget(Long budgetId);

    List<Budget> getBudgetsForUserAndMonth(Long userId,
                                           Integer year,
                                           Integer month);

    Optional<Budget> getBudgetForUserCategoryAndMonth(Long userId,
                                                      Long categoryId,
                                                      Integer year,
                                                      Integer month);
}

