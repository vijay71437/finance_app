package com.example.myFinance.service;



import com.example.myFinance.entity.DebtPayment;

import java.time.LocalDate;
import java.util.List;

public interface DebtPaymentService {

    DebtPayment addPaymentToDebt(Long debtId, DebtPayment payment);

    DebtPayment getPaymentById(Long paymentId);

    void deletePayment(Long paymentId);

    List<DebtPayment> getPaymentsForDebt(Long debtId);

    List<DebtPayment> getPaymentsForDebtInRange(Long debtId,
                                                LocalDate fromDate,
                                                LocalDate toDate);
}
