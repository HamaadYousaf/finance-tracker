package com.example.financetracker.subscription;

import com.example.financetracker.BillingCycle;
import com.example.financetracker.common.Category;
import com.example.financetracker.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;

    private LocalDate nextRenewalDate;

    @Enumerated(EnumType.STRING)
    private Category category;

    private boolean autoRenew;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
