package dev.kayange.projectmanagementsystem.dao;

import dev.kayange.projectmanagementsystem.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findSubscriptionByUserId(Long userId);
}
