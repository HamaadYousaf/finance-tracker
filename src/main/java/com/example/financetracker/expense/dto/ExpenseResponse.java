package com.example.financetracker.expense.dto;

import com.example.financetracker.common.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ExpenseResponse {

    private Long id;
    private BigDecimal amount;
    private Category category;
    private LocalDate date;
    private String description;
}