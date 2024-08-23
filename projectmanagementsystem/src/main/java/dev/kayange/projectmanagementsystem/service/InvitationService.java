package dev.kayange.projectmanagementsystem.service;


import jakarta.mail.MessagingException;

public interface InvitationService {
    void sendInvitation(String email, Long projectId) throws MessagingException;
    void acceptInvitation(String token, Long userId);
    String getTokenByUserEmail(String email);
    void deleteInvitation(String token);
}
