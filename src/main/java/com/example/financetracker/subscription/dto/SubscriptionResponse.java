package com.example.financetracker.subscription.dto;

import com.example.financetracker.BillingCycle;
import com.example.financetracker.common.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class SubscriptionResponse {

    private Long id;
    private String name;
    private BigDecimal cost;
    private BillingCycle billingCycle;
    private LocalDate nextRenewalDate;
    private Category category;
    private boolean autoRenew;
}
