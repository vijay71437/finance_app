package com.example.myFinance.service.Impl;

import com.example.myFinance.entity.Debt;
import com.example.myFinance.entity.DebtPayment;
import com.example.myFinance.exception.ResourceNotFoundException;
import com.example.myFinance.repository.DebtPaymentRepository;
import com.example.myFinance.repository.DebtRepository;
import com.example.myFinance.service.DebtPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DebtPaymentServiceImpl implements DebtPaymentService {

    private final DebtRepository debtRepository;
    private final DebtPaymentRepository debtPaymentRepository;

    @Override
    @Transactional
    public DebtPayment addPaymentToDebt(Long debtId, DebtPayment payment) {
        Debt debt = getDebtEntity(debtId);

        payment.setId(null);
        payment.setDebt(debt);

        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        }

        if (payment.getAmount() == null) {
            throw new IllegalArgumentException("Payment amount must not be null");
        }

        // Update outstanding amount
        BigDecimal currentOutstanding =
                debt.getOutstandingAmount() != null ? debt.getOutstandingAmount() : BigDecimal.ZERO;

        BigDecimal newOutstanding = currentOutstanding.subtract(payment.getAmount());
        if (newOutstanding.compareTo(BigDecimal.ZERO) < 0) {
            newOutstanding = BigDecimal.ZERO;
        }

        debt.setOutstandingAmount(newOutstanding);

        DebtPayment savedPayment = debtPaymentRepository.save(payment);
        debtRepository.save(debt);

        return savedPayment;
    }

    @Override
    public DebtPayment getPaymentById(Long paymentId) {
        return getPaymentEntity(paymentId);
    }

    @Override
    @Transactional
    public void deletePayment(Long paymentId) {
        DebtPayment existing = getPaymentEntity(paymentId);
        Debt debt = existing.getDebt();

        // Optionally: Add back to outstanding amount when deleting a payment
        if (debt != null && existing.getAmount() != null) {
            BigDecimal currentOutstanding =
                    debt.getOutstandingAmount() != null ? debt.getOutstandingAmount() : BigDecimal.ZERO;
            debt.setOutstandingAmount(currentOutstanding.add(existing.getAmount()));
            debtRepository.save(debt);
        }

        debtPaymentRepository.delete(existing);
    }

    @Override
    public List<DebtPayment> getPaymentsForDebt(Long debtId) {
        Debt debt = getDebtEntity(debtId);
        return debtPaymentRepository.findByDebt(debt);
    }

    @Override
    public List<DebtPayment> getPaymentsForDebtInRange(Long debtId,
                                                       LocalDate fromDate,
                                                       LocalDate toDate) {
        Debt debt = getDebtEntity(debtId);
        return debtPaymentRepository.findByDebtAndPaymentDateBetween(debt, fromDate, toDate);
    }

    // ---------- Helpers ----------

    private Debt getDebtEntity(Long debtId) {
        return debtRepository.findById(debtId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt not found with id: " + debtId));
    }

    private DebtPayment getPaymentEntity(Long paymentId) {
        return debtPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Debt payment not found with id: " + paymentId));
    }
}
