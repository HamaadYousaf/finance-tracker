package com.example.financetracker.subscription.dto;

import com.example.financetracker.BillingCycle;
import com.example.financetracker.common.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SubscriptionRequest {

    @NotNull
    private Long userId; // temporary until JWT

    @NotNull
    private String name;

    @NotNull
    private BigDecimal cost;

    @NotNull
    private BillingCycle billingCycle;

    @NotNull
    private LocalDate nextRenewalDate;

    @NotNull
    private Category category;

    private boolean autoRenew;
}
