package com.example.myFinance.service;

import com.example.myFinance.dto.DashboardSummaryDto;

public interface DashboardService {

    DashboardSummaryDto getMonthlySummary(Long userId, int year, int month);
}
