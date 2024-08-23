package dev.kayange.projectmanagementsystem.service;

import dev.kayange.projectmanagementsystem.dto.AuthResponse;
import dev.kayange.projectmanagementsystem.dto.LoginCredentials;
import dev.kayange.projectmanagementsystem.dto.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthService {

    void registerNewUser(RegistrationRequest request) throws MessagingException;

    AuthResponse authenticate(LoginCredentials credentials);

    void activateAccount(String token) throws MessagingException;
}
