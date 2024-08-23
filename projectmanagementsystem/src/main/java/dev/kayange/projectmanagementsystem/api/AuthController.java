package dev.kayange.projectmanagementsystem.api;

import dev.kayange.projectmanagementsystem.dto.ApiResponse;
import dev.kayange.projectmanagementsystem.dto.AuthResponse;
import dev.kayange.projectmanagementsystem.dto.LoginCredentials;
import dev.kayange.projectmanagementsystem.dto.RegistrationRequest;
import dev.kayange.projectmanagementsystem.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Tag(name = "Authentication", description = "The authentication API to use")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        authService.registerNewUser(request);
        ApiResponse<?> response = ApiResponse.builder()
                .status(HttpStatus.CREATED.name())
                .statusCode(HttpStatus.CREATED.value())
                .message("Account created successfully. Check your email for verification")
                .build();
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticate(@Valid @RequestBody LoginCredentials credentials){
        AuthResponse authenticate = authService.authenticate(credentials);
        ApiResponse<AuthResponse> response = ApiResponse
                .<AuthResponse>builder()
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .response(authenticate)
                .message("Login successful")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/activate-account")
    public ResponseEntity<?> activateAccount(@RequestParam("token") String token) throws MessagingException {
        authService.activateAccount(token);
        var response = ApiResponse.builder()
                .status(HttpStatus.OK.toString())
                .statusCode(HttpStatus.OK.value())
                .message("Token Verified Successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
