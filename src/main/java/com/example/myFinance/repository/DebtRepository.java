package com.example.myFinance.repository;


import com.example.myFinance.entity.Debt;
import com.example.myFinance.entity.User;
import com.example.myFinance.entity.enums.DebtType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {

    List<Debt> findByUser(User user);

    List<Debt> findByUserAndType(User user, DebtType type);
}

