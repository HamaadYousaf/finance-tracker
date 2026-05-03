package com.example.financetracker.expense.dto;

import com.example.financetracker.common.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRequest {

    @NotNull
    private Long userId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Category category;

    @NotNull
    private LocalDate date;

    private String description;
}
