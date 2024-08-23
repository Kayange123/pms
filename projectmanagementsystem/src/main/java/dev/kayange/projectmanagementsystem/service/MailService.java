package dev.kayange.projectmanagementsystem.service;

import dev.kayange.projectmanagementsystem.enumaration.EmailTemplate;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendMail(String to, String username, EmailTemplate emailTemplate, String confirmationUrl,
                  String activationCode, String subject) throws MessagingException;

}
