package dev.kayange.projectmanagementsystem.api;

import com.razorpay.RazorpayException;
import dev.kayange.projectmanagementsystem.dto.ApiResponse;
import dev.kayange.projectmanagementsystem.dto.response.PaymentLinkResponse;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.enumaration.PlanType;
import dev.kayange.projectmanagementsystem.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("payments")
@Tag(name = "Payment", description = "The Payment API to use")
public class PaymentController {
    private final PaymentService paymentService;


    @PostMapping("/{plan-type}")
    public ResponseEntity<ApiResponse<?>> createPaymentLink(
            @PathVariable("plan-type") PlanType planType,
            Authentication authentication
    ) throws RazorpayException {
        var user = (UserEntity) authentication.getPrincipal();
        PaymentLinkResponse linkResponse = paymentService.createPayment(user, planType);
        var response = ApiResponse.builder()
                .status(HttpStatus.CREATED.toString())
                .statusCode(HttpStatus.CREATED.value())
                .message("Payment Initiated Successfully")
                .response(linkResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
