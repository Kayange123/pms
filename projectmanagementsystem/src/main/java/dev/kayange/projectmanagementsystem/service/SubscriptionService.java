package dev.kayange.projectmanagementsystem.service;

import dev.kayange.projectmanagementsystem.entity.Subscription;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.enumaration.PlanType;

public interface SubscriptionService {
    void createSubscription(UserEntity user);
    Subscription getUserSubscription(Long userId);
    void upgradeSubscription(Long userId, PlanType planType);
    boolean isValidSubscription(Subscription subscription);
}
