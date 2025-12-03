package com.example.myFinance.repository;


import com.example.myFinance.entity.Budget;
import com.example.myFinance.entity.Category;
import com.example.myFinance.entity.User;
import com.example.myFinance.entity.enums.BudgetPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserAndYearAndMonth(User user, Integer year, Integer month);

    Optional<Budget> findByUserAndCategoryAndYearAndMonth(User user,
                                                          Category category,
                                                          Integer year,
                                                          Integer month);

    List<Budget> findByUserAndPeriod(User user, BudgetPeriod period);
}

