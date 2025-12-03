package com.example.myFinance.entity;



import com.example.myFinance.entity.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many accounts belong to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AccountType type;

    @Column(precision = 19, scale = 2)
    private BigDecimal initialBalance = BigDecimal.ZERO;

    @Column(precision = 19, scale = 2)
    private BigDecimal currentBalance = BigDecimal.ZERO;

    @Column(length = 10)
    private String currency = "INR";

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();
}
