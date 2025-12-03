package com.example.myFinance.service;



import com.example.myFinance.entity.Debt;

import java.util.List;

public interface DebtService {

    Debt createDebt(Long userId, Debt debt);

    Debt updateDebt(Long debtId, Debt updatedDebt);

    void deleteDebt(Long debtId);

    Debt getDebtById(Long debtId);

    List<Debt> getDebtsForUser(Long userId);
}

