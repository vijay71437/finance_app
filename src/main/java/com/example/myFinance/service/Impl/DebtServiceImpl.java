package com.example.myFinance.service.Impl;



import com.example.myFinance.entity.Debt;
import com.example.myFinance.entity.User;
import com.example.myFinance.exception.ResourceNotFoundException;
import com.example.myFinance.repository.DebtRepository;
import com.example.myFinance.repository.UserRepository;
import com.example.myFinance.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    private final UserRepository userRepository;

    @Override
    public Debt createDebt(Long userId, Debt debt) {
        User user = getUserEntity(userId);

        debt.setId(null);
        debt.setUser(user);

        // If outstanding not set, assume full total amount is outstanding
        if (debt.getOutstandingAmount() == null) {
            debt.setOutstandingAmount(
                    debt.getTotalAmount() != null ? debt.getTotalAmount() : BigDecimal.ZERO
            );
        }

        return debtRepository.save(debt);
    }

    @Override
    public Debt updateDebt(Long debtId, Debt updatedDebt) {
        Debt existing = getDebtEntity(debtId);

        existing.setName(updatedDebt.getName());
        existing.setType(updatedDebt.getType());
        existing.setTotalAmount(updatedDebt.getTotalAmount());
        existing.setOutstandingAmount(updatedDebt.getOutstandingAmount());
        existing.setInterestRate(updatedDebt.getInterestRate());
        existing.setEmiAmount(updatedDebt.getEmiAmount());
        existing.setDueDayOfMonth(updatedDebt.getDueDayOfMonth());
        existing.setStartDate(updatedDebt.getStartDate());
        existing.setExpectedEndDate(updatedDebt.getExpectedEndDate());

        return debtRepository.save(existing);
    }

    @Override
    public void deleteDebt(Long debtId) {
        Debt existing = getDebtEntity(debtId);
        debtRepository.delete(existing);
    }

    @Override
    public Debt getDebtById(Long debtId) {
        return getDebtEntity(debtId);
    }

    @Override
    public List<Debt> getDebtsForUser(Long userId) {
        User user = getUserEntity(userId);
        return debtRepository.findByUser(user);
    }

    // ---------- Helpers ----------

    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private Debt getDebtEntity(Long debtId) {
        return debtRepository.findById(debtId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found with id: " + debtId));
    }
}
