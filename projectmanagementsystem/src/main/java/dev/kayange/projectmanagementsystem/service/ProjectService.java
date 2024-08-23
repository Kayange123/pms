package dev.kayange.projectmanagementsystem.service;

import dev.kayange.projectmanagementsystem.dto.request.InvitationRequest;
import dev.kayange.projectmanagementsystem.dto.request.ProjectRequest;
import dev.kayange.projectmanagementsystem.entity.Chat;
import dev.kayange.projectmanagementsystem.entity.Project;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {
    void createProject(ProjectRequest request, Long userId);
    Project getProjectById(Long id);
    void addUserToProject(Long projectId, Long userId);
    void removeUserFromProject(Long projectId, Long userId);
    List<Project> getProjectsByTeam(UserEntity user, String category, String tag);
    List<Project> searchProjects(String keyword, UserEntity user);
    void updateProjectDetails(ProjectRequest request, Long projectId, Long userId);
    void deleteProject(Long projectId, Long id);
    Chat getProjectChat(Long projectId);
    void inviteMember(InvitationRequest request, UserEntity user) throws MessagingException;
    void acceptInvite(String token, UserEntity user);

    Page<Project> getAllProjects(int page, int size);
}
