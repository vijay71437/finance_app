package com.example.myFinance.controller;

import com.example.myFinance.entity.DebtPayment;
import com.example.myFinance.service.DebtPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DebtPaymentController {

    private final DebtPaymentService debtPaymentService;

    // Add payment to a debt
    @PostMapping("/debts/{debtId}/payments")
    public ResponseEntity<DebtPayment> addPaymentToDebt(@PathVariable Long debtId,
                                                        @RequestBody DebtPayment payment) {
        DebtPayment created = debtPaymentService.addPaymentToDebt(debtId, payment);
        return ResponseEntity.ok(created);
    }

    // Get a single payment
    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<DebtPayment> getPayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(debtPaymentService.getPaymentById(paymentId));
    }

    // Delete a payment
    @DeleteMapping("/payments/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId) {
        debtPaymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }

    // Get all payments for a debt
    @GetMapping("/debts/{debtId}/payments")
    public ResponseEntity<List<DebtPayment>> getPaymentsForDebt(@PathVariable Long debtId) {
        return ResponseEntity.ok(debtPaymentService.getPaymentsForDebt(debtId));
    }

    // Get payments for a debt in a date range
    @GetMapping("/debts/{debtId}/payments/range")
    public ResponseEntity<List<DebtPayment>> getPaymentsForDebtInRange(
            @PathVariable Long debtId,
            @RequestParam("from")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam("to")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        List<DebtPayment> payments =
                debtPaymentService.getPaymentsForDebtInRange(debtId, fromDate, toDate);

        return ResponseEntity.ok(payments);
    }
}
