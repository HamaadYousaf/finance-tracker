package com.example.financetracker.subscription;

import com.example.financetracker.subscription.dto.SubscriptionRequest;
import com.example.financetracker.subscription.dto.SubscriptionResponse;
import com.example.financetracker.user.User;
import com.example.financetracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @CacheEvict(value = "dashboard", key = "#request.userId")
    public SubscriptionResponse addSubscription(SubscriptionRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription sub = new Subscription();
        sub.setName(request.getName());
        sub.setCost(request.getCost());
        sub.setBillingCycle(request.getBillingCycle());
        sub.setNextRenewalDate(request.getNextRenewalDate());
        sub.setCategory(request.getCategory());
        sub.setAutoRenew(request.isAutoRenew());
        sub.setUser(user);

        Subscription saved = subscriptionRepository.save(sub);

        return mapToResponse(saved);
    }

    public List<SubscriptionResponse> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @CacheEvict(value = "dashboard", key = "#request.userId")
    public SubscriptionResponse updateSubscription(Long id, SubscriptionRequest request) {

        Subscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        sub.setName(request.getName());
        sub.setCost(request.getCost());
        sub.setBillingCycle(request.getBillingCycle());
        sub.setNextRenewalDate(request.getNextRenewalDate());
        sub.setCategory(request.getCategory());
        sub.setAutoRenew(request.isAutoRenew());

        return mapToResponse(subscriptionRepository.save(sub));
    }

    @CacheEvict(value = "dashboard", key = "#userId")
    public void deleteSubscription(Long userId, Long id) {
        subscriptionRepository.deleteById(id);
    }

    public List<SubscriptionResponse> getUpcomingRenewals(Long userId) {
        LocalDate now = LocalDate.now();
        LocalDate nextWeek = now.plusDays(7);

        return subscriptionRepository
                .findByUserIdAndNextRenewalDateBetween(userId, now, nextWeek)
                .stream()
                .map(this::mapToResponse)
                .toList();
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
