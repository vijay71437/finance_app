package com.example.myFinance.repository;




import com.example.myFinance.entity.Debt;
import com.example.myFinance.entity.DebtPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DebtPaymentRepository extends JpaRepository<DebtPayment, Long> {

    List<DebtPayment> findByDebt(Debt debt);

    List<DebtPayment> findByDebtAndPaymentDateBetween(Debt debt,
                                                      LocalDate from,
                                                      LocalDate to);
}

