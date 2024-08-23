package dev.kayange.projectmanagementsystem.service.implementation;

import dev.kayange.projectmanagementsystem.dao.InvitationRepository;
import dev.kayange.projectmanagementsystem.entity.Invitation;
import dev.kayange.projectmanagementsystem.enumaration.EmailTemplate;
import dev.kayange.projectmanagementsystem.enumaration.InvitationStatus;
import dev.kayange.projectmanagementsystem.exception.ApiException;
import dev.kayange.projectmanagementsystem.service.InvitationService;
import dev.kayange.projectmanagementsystem.service.MailService;
import dev.kayange.projectmanagementsystem.utils.EmailValidator;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.JdkIdGenerator;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final InvitationRepository invitationRepository;
    private final MailService mailService;
    private final EmailValidator emailValidator;

    @Value("${application.mailing.front-end.url}")
    private String invitationUrl;

    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        var invitation = Invitation.builder()
                .email(email)
                .status(InvitationStatus.PENDING)
                .projectId(projectId)
                .token(new JdkIdGenerator().toString())
                .build();
        Invitation savedInvitation = invitationRepository.save(invitation);
        String link = invitationUrl + "/invitations/accept?token" + savedInvitation.getToken();
        String SUBJECT = "Invitation To Join Project";
        mailService.sendMail(email, "Project Invitee", EmailTemplate.INVITATION, link, EMPTY, SUBJECT);
    }

    @Override
    public void acceptInvitation(String token, Long userId) {
        var invitation = findInvitationByToken(token);
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);
    }

    private Invitation findInvitationByToken(String token) {
        return invitationRepository.findInvitationByToken(token).orElseThrow(()-> new ApiException("Could not find token"));
    }

    @Override
    public String getTokenByUserEmail(String email) {
        var validEmail = emailValidator.test(email);
        if (!validEmail) throw new ApiException("Invalid email");
        return invitationRepository.findInvitationByEmail(email).orElseThrow(()-> new ApiException("Could not find")).getToken();
    }

    @Override
    public void deleteInvitation(String token) {
        Invitation invitationByToken = findInvitationByToken(token);
        invitationRepository.delete(invitationByToken);
    }
}
