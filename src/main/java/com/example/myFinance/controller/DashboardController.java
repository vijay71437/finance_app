package com.example.myFinance.controller;

import com.example.myFinance.dto.DashboardSummaryDto;
import com.example.myFinance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    // Example:
    // GET /api/users/1/dashboard?year=2025&month=12
    @GetMapping("/users/{userId}/dashboard")
    public ResponseEntity<DashboardSummaryDto> getMonthlyDashboard(
            @PathVariable Long userId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {

        DashboardSummaryDto summary =
                dashboardService.getMonthlySummary(userId, year, month);

        return ResponseEntity.ok(summary);
    }
}
