package dev.kayange.projectmanagementsystem.service;

import com.razorpay.RazorpayException;
import dev.kayange.projectmanagementsystem.dto.response.PaymentLinkResponse;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.enumaration.PlanType;

public interface PaymentService {
    PaymentLinkResponse createPayment(UserEntity user, PlanType planType) throws RazorpayException;
}
