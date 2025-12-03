package com.example.myFinance.service.Impl;

import com.example.myFinance.entity.Budget;
import com.example.myFinance.entity.Category;
import com.example.myFinance.entity.User;
import com.example.myFinance.exception.ResourceNotFoundException;
import com.example.myFinance.repository.BudgetRepository;
import com.example.myFinance.repository.CategoryRepository;
import com.example.myFinance.repository.UserRepository;
import com.example.myFinance.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Budget createOrUpdateBudget(Long userId, Budget budget) {
        User user = getUserEntity(userId);

        if (budget.getCategory() == null || budget.getCategory().getId() == null) {
            throw new IllegalArgumentException("Budget must contain a valid category with ID");
        }

        Category category = getCategoryEntity(budget.getCategory().getId());

        // If year/month not provided, you can add defaults if you want
        if (budget.getYear() == null || budget.getMonth() == null) {
            throw new IllegalArgumentException("Budget year and month must not be null");
        }

        Optional<Budget> existingOpt = budgetRepository
                .findByUserAndCategoryAndYearAndMonth(user, category, budget.getYear(), budget.getMonth());

        Budget toSave;
        if (existingOpt.isPresent()) {
            // Update existing budget
            Budget existing = existingOpt.get();
            existing.setLimitAmount(budget.getLimitAmount());
            existing.setPeriod(budget.getPeriod());
            toSave = existing;
        } else {
            // Create new budget
            budget.setId(null);
            budget.setUser(user);
            budget.setCategory(category);
            toSave = budget;
        }

        return budgetRepository.save(toSave);
    }

    @Override
    public Budget getBudgetById(Long budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + budgetId));
    }

    @Override
    public void deleteBudget(Long budgetId) {
        if (!budgetRepository.existsById(budgetId)) {
            throw new ResourceNotFoundException("Budget not found with id: " + budgetId);
        }
        budgetRepository.deleteById(budgetId);
    }

    @Override
    public List<Budget> getBudgetsForUserAndMonth(Long userId, Integer year, Integer month) {
        User user = getUserEntity(userId);
        return budgetRepository.findByUserAndYearAndMonth(user, year, month);
    }

    @Override
    public Optional<Budget> getBudgetForUserCategoryAndMonth(Long userId,
                                                             Long categoryId,
                                                             Integer year,
                                                             Integer month) {
        User user = getUserEntity(userId);
        Category category = getCategoryEntity(categoryId);

        return budgetRepository.findByUserAndCategoryAndYearAndMonth(user, category, year, month);
    }

    // ---------- Helpers ----------

    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private Category getCategoryEntity(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
    }
}
