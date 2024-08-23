package dev.kayange.projectmanagementsystem.service.implementation;

import dev.kayange.projectmanagementsystem.dao.TokenRepository;
import dev.kayange.projectmanagementsystem.dao.UserRepository;
import dev.kayange.projectmanagementsystem.dto.AuthResponse;
import dev.kayange.projectmanagementsystem.dto.LoginCredentials;
import dev.kayange.projectmanagementsystem.dto.RegistrationRequest;
import dev.kayange.projectmanagementsystem.entity.Token;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.enumaration.EmailTemplate;
import dev.kayange.projectmanagementsystem.exception.ApiException;
import dev.kayange.projectmanagementsystem.security.JwtService;
import dev.kayange.projectmanagementsystem.service.AuthService;
import dev.kayange.projectmanagementsystem.service.MailService;
import dev.kayange.projectmanagementsystem.service.SubscriptionService;
import dev.kayange.projectmanagementsystem.utils.EmailValidator;
import dev.kayange.projectmanagementsystem.utils.Utils;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final MailService mailService;
    private final EmailValidator emailValidator;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SubscriptionService subscriptionService;

    @Value("${application.mailing.front-end.url}")
    private String confirmationUrl;

    @Override
    public void registerNewUser(RegistrationRequest request) throws MessagingException {

        var isEmail = emailValidator.test(request.getEmail());
        if(!isEmail){
            throw new ApiException("You entered invalid email address");
        }
        var userExists = userRepository.findByEmail(request.getEmail());
        if(userExists.isPresent()){
            throw new ApiException("User with email "+ request.getEmail()+ " already exists");
        }
        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString().replace("-", ""))
                .projectSize(0)
                .enabled(false)
                .locked(false)
                .assignedIssues(List.of())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        var userName = generateUserName(request);
        user.setUsername(userName);
        subscriptionService.createSubscription(user);
        log.info("User created "+ user);
        String token = generateAndSaveToken(user);
        sendEmail(user, token);
    }

    @Override
    public AuthResponse authenticate(LoginCredentials credentials) {
        boolean isValidEmail = emailValidator.test(credentials.getEmail());
        if(!isValidEmail) throw new ApiException("Invalid email");
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
        var claims = new HashMap<String, Object>();
        var user = (UserEntity) auth.getPrincipal();
        claims.put("fullName", user.getFullName());
        String token = jwtService.generateToken(claims, user);
        return AuthResponse.builder().token(token).build();
    }

    @Override
    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(() -> new ApiException("Could not find the token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            //If token is expired then generate a new token and send to user's email
            var newToken = generateAndSaveToken(savedToken.getUser());
            tokenRepository.delete(savedToken);
            sendEmail(savedToken.getUser(), newToken);
            throw new ApiException("Token already expired. New token was sent to your email");
        }
        UserEntity user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(()-> new ApiException("User for this token was NOT found"));
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(savedToken);
    }

    private String generateUserName(RegistrationRequest registration) {
        ///TODO: Generate the UNIQUE username for the USER
        return (registration.getFirstName().toLowerCase().concat(registration.getLastName().toLowerCase())).replace(" ", "_");
    }

    private String generateAndSaveToken(UserEntity user){
        var otp = Utils.generateActivationOtp(6);
        var token = Token.builder()
                .token(otp)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        log.info(otp);
        return otp;
    }

    private void sendEmail(UserEntity user, String token) throws MessagingException {

        mailService.sendMail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                confirmationUrl,
                token,
                "Account Activation"
        );
    }
}
