package com.example.myFinance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CategorySummaryDto {
    private Long categoryId;
    private String categoryName;
    private BigDecimal totalAmount;
}
