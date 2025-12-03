package com.example.myFinance.entity;


import com.example.myFinance.entity.enums.BudgetPeriod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "budgets",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "category_id", "year", "month"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User who owns this budget
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Category this budget is for (e.g. FOOD)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BudgetPeriod period = BudgetPeriod.MONTHLY;

    // For monthly budgets
    private Integer year;
    private Integer month; // 1-12

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal limitAmount;
}

