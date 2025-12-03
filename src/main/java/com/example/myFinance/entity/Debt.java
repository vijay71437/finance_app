package com.example.myFinance.entity;



import com.example.myFinance.entity.enums.DebtType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "debts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each debt belongs to a user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String name; // e.g. HDFC Personal Loan

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DebtType type;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 19, scale = 2)
    private BigDecimal outstandingAmount;

    @Column(precision = 5, scale = 2)
    private BigDecimal interestRate; // Annual %

    @Column(precision = 19, scale = 2)
    private BigDecimal emiAmount;

    private Integer dueDayOfMonth; // 1-31

    private LocalDate startDate;
    private LocalDate expectedEndDate;

    @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DebtPayment> payments = new ArrayList<>();
}

