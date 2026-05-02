package com.example.financetracker.dashboard;

import com.example.financetracker.common.Category;
import com.example.financetracker.subscription.dto.SubscriptionResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardResponse {

    private BigDecimal totalMonthlySubscriptionCost;
    private BigDecimal totalExpensesThisMonth;
    private List<SubscriptionResponse> upcomingRenewals;
    private Map<Category, BigDecimal> categoryBreakdown;
}
