package com.example.myFinance.repository;


import com.example.myFinance.entity.Account;
import com.example.myFinance.entity.Category;
import com.example.myFinance.entity.Transaction;
import com.example.myFinance.entity.User;
import com.example.myFinance.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserAndDateTimeBetween(User user,
                                                   LocalDateTime from,
                                                   LocalDateTime to);

    List<Transaction> findByUserAndAccountAndDateTimeBetween(User user,
                                                             Account account,
                                                             LocalDateTime from,
                                                             LocalDateTime to);

    List<Transaction> findByUserAndCategoryAndDateTimeBetween(User user,
                                                              Category category,
                                                              LocalDateTime from,
                                                              LocalDateTime to);

    List<Transaction> findByUserAndTypeAndDateTimeBetween(User user,
                                                          TransactionType type,
                                                          LocalDateTime from,
                                                          LocalDateTime to);
}

