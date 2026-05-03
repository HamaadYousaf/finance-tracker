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
        // 1️⃣ Monthly Subscription Cost
        // =========================
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);

        BigDecimal totalMonthlySubscriptionCost = subscriptions.stream()
                .map(sub -> {
                    if (sub.getBillingCycle() == BillingCycle.YEARLY) {
                        return sub.getCost().divide(BigDecimal.valueOf(12));
                    }
                    return sub.getCost();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // =========================
        // 2️⃣ Expenses This Month
        // =========================
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);

        List<Expense> expensesThisMonth =
                expenseRepository.findByUserIdAndDateBetween(userId, startOfMonth, now);

        BigDecimal totalExpensesThisMonth = expensesThisMonth.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // =========================
        // 3️⃣ Upcoming Renewals (next 7 days)
        // =========================
        LocalDate nextWeek = now.plusDays(7);

        List<SubscriptionResponse> upcomingRenewals =
                subscriptionRepository
                        .findByUserIdAndNextRenewalDateBetween(userId, now, nextWeek)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        // =========================
        // 4️⃣ Category Breakdown (Expenses)
        // =========================
        Map<Category, BigDecimal> categoryBreakdown =
                expensesThisMonth.stream()
                        .collect(Collectors.groupingBy(
                                Expense::getCategory,
                                Collectors.mapping(
                                        Expense::getAmount,
                                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                                )
                        ));

        return DashboardResponse.builder()
                .totalMonthlySubscriptionCost(totalMonthlySubscriptionCost)
                .totalExpensesThisMonth(totalExpensesThisMonth)
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
