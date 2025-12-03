package com.example.myFinance.repository;


import com.example.myFinance.entity.Account;
import com.example.myFinance.entity.User;
import com.example.myFinance.entity.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUser(User user);

    List<Account> findByUserAndActiveTrue(User user);

    List<Account> findByUserAndType(User user, AccountType type);
}

