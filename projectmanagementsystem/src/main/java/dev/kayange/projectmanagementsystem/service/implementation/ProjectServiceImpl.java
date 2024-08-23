package dev.kayange.projectmanagementsystem.service.implementation;

import dev.kayange.projectmanagementsystem.dao.ProjectRepository;
import dev.kayange.projectmanagementsystem.dto.request.InvitationRequest;
import dev.kayange.projectmanagementsystem.dto.request.ProjectRequest;
import dev.kayange.projectmanagementsystem.entity.Chat;
import dev.kayange.projectmanagementsystem.entity.Project;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.exception.ApiException;
import dev.kayange.projectmanagementsystem.service.ChatService;
import dev.kayange.projectmanagementsystem.service.InvitationService;
import dev.kayange.projectmanagementsystem.service.ProjectService;
import dev.kayange.projectmanagementsystem.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.JdkIdGenerator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ChatService chatService;
    private final InvitationService invitationService;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createProject(ProjectRequest request, Long userId) {
        UserEntity user = userService.findUserById(userId);
        Project project = Project.builder()
                .description(request.getDescription())
                .name(request.getName())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .tags(request.getTags())
                .owner(user)
                .publicId(UUID.randomUUID().toString().replace("-", ""))
                .team(List.of(user))
                .build();
        var savedProject = projectRepository.save(project);
        var chat = Chat.builder().project(savedProject).build();
        chatService.createChat(chat);
    }

    @Override
    public Project getProjectById(Long id) {
       // var project = findProjectById(id);
        return findProjectById(id);
    }

    private Project findProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(()->new ApiException("Could not find project with ID "+ id));
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) {
        var user = userService.findUserById(userId);
        var project = findProjectById(projectId);

        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getChat().getUsers().add(user);
        }else {
            throw new ApiException("User is already in project");
        }
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) {
        var user = userService.findUserById(userId);
        var project = findProjectById(projectId);

        if(project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getChat().getUsers().remove(user);
        }else {
            throw new ApiException("User is NOT in project");
        }
        projectRepository.save(project);
    }

    @Override

    public List<Project> getProjectsByTeam(UserEntity user, String category, String tag) {
        List<Project> projects = projectRepository.findProjectByTeamContainingOrOwner(user, user);
        if(category != null){
           projects = projects.stream().filter(project -> project.getCategory().equals(category)).collect(Collectors.toList());
        }

        if (tag != null){
           projects = projects.stream().filter(project -> project.getTags().contains(tag)).collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public List<Project> searchProjects(String keyword, UserEntity user) {
        return projectRepository.findProjectByNameContainingAndTeamContains(keyword, user);
    }

    @Override
    public void updateProjectDetails(ProjectRequest request, Long projectId, Long userId) {
        var user = userService.findUserById(userId);
        var project = findProjectById(projectId);
        if(!Objects.equals(project.getOwner().getId(), user.getId())) throw new ApiException("You are not authorized to edit this project");
        if(Objects.nonNull(request.getName())) project.setName(request.getName());
        if(Objects.nonNull(request.getDescription())) project.setDescription(request.getDescription());
        if(Objects.nonNull(request.getCategory())) project.setCategory(request.getCategory());
        if(Objects.nonNull(request.getImageUrl())) project.setImageUrl(request.getImageUrl());
        if(Objects.nonNull(request.getTags())) project.setTags(request.getTags());

        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId, Long id) {
        var user = userService.findUserById(id);
        var project = findProjectById(projectId);
        if(!Objects.equals(user.getId(), project.getOwner().getId())) throw new ApiException("You are not Authorized to delete this project");

        projectRepository.deleteById(project.getId());
    }

    @Override
    public Chat getProjectChat(Long projectId) {
        Project project = findProjectById(projectId);
        return project.getChat();
    }

    @Override
    public void inviteMember(InvitationRequest request, UserEntity user) throws MessagingException {
        var project = findProjectById(request.getProjectId());
        if(!Objects.equals(project.getOwner().getId(), user.getId())) throw new ApiException("You are not Authorized to invite members for this project");
        invitationService.sendInvitation(request.getEmail(), request.getProjectId());
    }

    @Override
    public void acceptInvite(String token, UserEntity user) {
        invitationService.acceptInvitation(token, user.getId());
    }

    @Override
    public Page<Project> getAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return projectRepository.findAll(pageable);
    }
}
