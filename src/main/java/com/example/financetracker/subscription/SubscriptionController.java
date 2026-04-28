package com.example.financetracker.subscription;

import com.example.financetracker.subscription.dto.SubscriptionRequest;
import com.example.financetracker.subscription.dto.SubscriptionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    // ✅ Create subscription
    @PostMapping
    public ResponseEntity<SubscriptionResponse> addSubscription(
            @Valid @RequestBody SubscriptionRequest request) {

        return ResponseEntity.ok(subscriptionService.addSubscription(request));
    }

    // ✅ Get all subscriptions for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptions(
            @PathVariable Long userId) {

        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId));
    }

    // ✅ Update subscription
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> updateSubscription(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionRequest request) {

        return ResponseEntity.ok(subscriptionService.updateSubscription(id, request));
    }

    // ✅ Delete subscription
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Upcoming renewals (next 7 days)
    @GetMapping("/{userId}/upcoming")
    public ResponseEntity<List<SubscriptionResponse>> getUpcomingRenewals(
            @PathVariable Long userId) {

        return ResponseEntity.ok(subscriptionService.getUpcomingRenewals(userId));
    }
}
