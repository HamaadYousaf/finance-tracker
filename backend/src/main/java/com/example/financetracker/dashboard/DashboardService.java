package com.example.financetracker.dashboard;

import com.example.financetracker.BillingCycle;
import com.example.financetracker.common.Category;
import com.example.financetracker.expense.Expense;
import com.example.financetracker.expense.ExpenseRepository;
import com.example.financetracker.subscription.Subscription;
import com.example.financetracker.subscription.SubscriptionRepository;
import com.example.financetracker.subscription.dto.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final SubscriptionRepository subscriptionRepository;
    private final ExpenseRepository expenseRepository;

    @Cacheable(value = "dashboard", key = "#userId")
    public DashboardResponse getDashboard(Long userId) {

        // =========================
        // 1️⃣ ALL Subscriptions
        // =========================
        List<Subscription> subscriptions =
                subscriptionRepository.findByUser_Id(userId);

        BigDecimal totalMonthlySubscriptionCost = subscriptions.stream()
                .map(sub -> {
                    if (sub.getBillingCycle() == BillingCycle.YEARLY) {
                        return sub.getCost().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
                    }
                    return sub.getCost();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // =========================
        // 2️⃣ ALL Expenses (NO MONTH FILTER)
        // =========================
        List<Expense> allExpenses =
                expenseRepository.findByUser_Id(userId);

        BigDecimal totalExpenses = allExpenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // =========================
        // 3️⃣ ALL Upcoming Renewals (or just ALL subscriptions if you prefer)
        // =========================
        List<SubscriptionResponse> upcomingRenewals =
                subscriptions.stream()
                        .map(this::mapToResponse)
                        .toList();

        // =========================
        // 4️⃣ Category Breakdown (ALL expenses)
        // =========================
        Map<Category, BigDecimal> categoryBreakdown =
                allExpenses.stream()
                        .collect(Collectors.groupingBy(
                                Expense::getCategory,
                                Collectors.mapping(
                                        Expense::getAmount,
                                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                                )
                        ));

        // =========================
        // RESPONSE
        // =========================
        return DashboardResponse.builder()
                .totalMonthlySubscriptionCost(totalMonthlySubscriptionCost)
                .totalExpensesThisMonth(totalExpenses)
                .upcomingRenewals(upcomingRenewals)
                .categoryBreakdown(categoryBreakdown)
                .build();
    }

    private SubscriptionResponse mapToResponse(Subscription sub) {
        return SubscriptionResponse.builder()
                .id(sub.getId())
                .name(sub.getName())
                .cost(sub.getCost())
                .billingCycle(sub.getBillingCycle())
                .nextRenewalDate(sub.getNextRenewalDate())
                .category(sub.getCategory())
                .autoRenew(sub.isAutoRenew())
                .build();
    }
}
