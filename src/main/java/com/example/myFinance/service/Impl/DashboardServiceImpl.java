package com.example.myFinance.service.Impl;

import com.example.myFinance.dto.CategorySummaryDto;
import com.example.myFinance.dto.DashboardSummaryDto;
import com.example.myFinance.entity.Debt;
import com.example.myFinance.entity.Transaction;
import com.example.myFinance.entity.User;
import com.example.myFinance.entity.enums.TransactionType;
import com.example.myFinance.exception.ResourceNotFoundException;
import com.example.myFinance.repository.DebtRepository;
import com.example.myFinance.repository.TransactionRepository;
import com.example.myFinance.repository.UserRepository;
import com.example.myFinance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final DebtRepository debtRepository;

    @Override
    public DashboardSummaryDto getMonthlySummary(Long userId, int year, int month) {
        User user = getUserEntity(userId);

        // Date range for the given month
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to = from.plusMonths(1).minusDays(1);

        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);

        // All transactions for the month
        List<Transaction> transactions =
                transactionRepository.findByUserAndDateTimeBetween(user, fromDateTime, toDateTime);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        // Category-wise expense map
        Map<Long, CategorySummaryDto> categoryMap = new HashMap<>();

        for (Transaction tx : transactions) {
            if (tx.getAmount() == null || tx.getType() == null) continue;

            if (tx.getType() == TransactionType.INCOME) {
                totalIncome = totalIncome.add(tx.getAmount());
            } else if (tx.getType() == TransactionType.EXPENSE) {
                totalExpense = totalExpense.add(tx.getAmount());

                if (tx.getCategory() != null) {
                    Long catId = tx.getCategory().getId();
                    String catName = tx.getCategory().getName();

                    CategorySummaryDto existing = categoryMap.get(catId);
                    if (existing == null) {
                        categoryMap.put(
                                catId,
                                new CategorySummaryDto(catId, catName, tx.getAmount())
                        );
                    } else {
                        existing.setTotalAmount(existing.getTotalAmount().add(tx.getAmount()));
                    }
                }
            }
        }

        // Total EMI this month = sum of emiAmount for all debts of user (simple version)
        List<Debt> debts = debtRepository.findByUser(user);
        BigDecimal totalEmiThisMonth = debts.stream()
                .map(Debt::getEmiAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal net = totalIncome.subtract(totalExpense);

        List<CategorySummaryDto> categoryExpenseBreakdown =
                categoryMap.values()
                        .stream()
                        .sorted(Comparator.comparing(CategorySummaryDto::getTotalAmount).reversed())
                        .collect(Collectors.toList());

        return DashboardSummaryDto.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .net(net)
                .totalEmiThisMonth(totalEmiThisMonth)
                .categoryExpenseBreakdown(categoryExpenseBreakdown)
                .build();
    }

    // ---------- Helpers ----------

    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
}
