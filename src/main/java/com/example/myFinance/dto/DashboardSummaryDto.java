package com.example.myFinance.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DashboardSummaryDto {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal net; // income - expense

    private BigDecimal totalEmiThisMonth;

    private List<CategorySummaryDto> categoryExpenseBreakdown;
}
