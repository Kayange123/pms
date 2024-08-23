package dev.kayange.projectmanagementsystem.service.implementation;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import dev.kayange.projectmanagementsystem.dto.response.PaymentLinkResponse;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.enumaration.PlanType;
import dev.kayange.projectmanagementsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    @Value("${razorpay.api.key}")
    private String apiKey;
    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Override
    public PaymentLinkResponse createPayment(UserEntity user, PlanType planType) throws RazorpayException {
        double amount = 300.0;
        if(planType.equals(PlanType.ANNUALLy)){
            amount= amount*12;
            amount = amount * 0.7;
        }
            var razorpayClient = new RazorpayClient(apiKey, apiSecret);
            var paymentLink = new JSONObject();
            paymentLink.put("amount", amount);
            paymentLink.put("currency","USD");

            var customer = new JSONObject();
            customer.put("fullName", user.getFullName());
            customer.put("email", user.getEmail());
            paymentLink.put("customer", customer);

            var notify = new JSONObject();
            notify.put("email", true);
            paymentLink.put("notify", notify);
            String callbackUrl = "http://localhost:3000/upgrade_plan/success?plan-type=";
            paymentLink.put("callback_url", callbackUrl +planType.name());
            PaymentLink link = razorpayClient.paymentLink.create(paymentLink);

            String paymentLinkUrl = link.get("short_url");
            String paymentLinkId = link.get("id");

            var paymentLinkResponse = new PaymentLinkResponse();
            paymentLinkResponse.setPayment_link_id(paymentLinkId);
            paymentLinkResponse.setPayment_link_url(paymentLinkUrl);

            return paymentLinkResponse;

    }
}
