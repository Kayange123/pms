package dev.kayange.projectmanagementsystem.api;

import dev.kayange.projectmanagementsystem.dto.ApiResponse;
import dev.kayange.projectmanagementsystem.entity.Subscription;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.enumaration.PlanType;
import dev.kayange.projectmanagementsystem.service.SubscriptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("subscriptions")
@Tag(name = "Subscriptions", description = "The Subscription API to use")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<?>> getUserSubscription(Authentication authentication){
        var user = (UserEntity) authentication.getPrincipal();
        Subscription subscription = subscriptionService.getUserSubscription(user.getId());
        ApiResponse<?> response = ApiResponse
                .builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Subscription Fetched successfully")
                .response(subscription)
                .build();
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/upgrade")
    public ResponseEntity<ApiResponse<?>> upgradePlanType(@RequestParam PlanType planType, Authentication authentication){
        var user = (UserEntity) authentication.getPrincipal();
        subscriptionService.upgradeSubscription(user.getId(), planType);
        ApiResponse<?> response = ApiResponse
                .builder()
                .status(HttpStatus.OK.name())
                .statusCode(HttpStatus.OK.value())
                .message("Plan upgraded successfully")
                .build();
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

}
