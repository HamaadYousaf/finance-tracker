package com.example.financetracker.dashboard;

import com.example.financetracker.common.Category;
import com.example.financetracker.subscription.dto.SubscriptionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private BigDecimal totalMonthlySubscriptionCost;
    private BigDecimal totalExpensesThisMonth;
    private List<SubscriptionResponse> upcomingRenewals;
    private Map<Category, BigDecimal> categoryBreakdown;
}
