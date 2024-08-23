package dev.kayange.projectmanagementsystem.service.implementation;

import dev.kayange.projectmanagementsystem.dao.SubscriptionRepository;
import dev.kayange.projectmanagementsystem.entity.Subscription;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.enumaration.PlanType;
import dev.kayange.projectmanagementsystem.exception.ApiException;
import dev.kayange.projectmanagementsystem.service.SubscriptionService;
import dev.kayange.projectmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    @Override
    public void createSubscription(UserEntity user) {
        var subscription = Subscription.builder().user(user)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(12))
                .valid(true)
                .planType(PlanType.FREE)
                .build();
        subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) {
        var user = userService.findUserById(userId);
        var subscription = subscriptionRepository.findSubscriptionByUserId(user.getId()).orElseThrow(()-> new ApiException("Could NOT find subscription"));
        if(!isValidSubscription(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setStartDate(LocalDate.now());
            subscription.setEndDate(LocalDate.now().plusMonths(12));
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void upgradeSubscription(Long userId, PlanType planType) {
        var subscription = getUserSubscription(userId);
        subscription.setPlanType(planType);
        subscription.setStartDate(LocalDate.now());
        switch (planType) {
            case MONTHLY -> subscription.setEndDate(LocalDate.now().plusMonths(1));
            case ANNUALLy -> subscription.setEndDate(LocalDate.now().plusMonths(12));
            default -> {}
        }
    }

    @Override
    public boolean isValidSubscription(Subscription subscription) {
        if(subscription.getPlanType().equals(PlanType.FREE)) return true;
        return (subscription.getEndDate().isAfter(LocalDate.now()) || subscription.getEndDate().isEqual(LocalDate.now()));
    }
}
