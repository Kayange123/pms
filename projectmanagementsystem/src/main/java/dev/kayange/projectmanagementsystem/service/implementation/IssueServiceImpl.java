package dev.kayange.projectmanagementsystem.service.implementation;

import dev.kayange.projectmanagementsystem.dao.IssueRepository;
import dev.kayange.projectmanagementsystem.dto.request.IssueRequest;
import dev.kayange.projectmanagementsystem.entity.Issue;
import dev.kayange.projectmanagementsystem.entity.Project;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import dev.kayange.projectmanagementsystem.exception.ApiException;
import dev.kayange.projectmanagementsystem.service.IssueService;
import dev.kayange.projectmanagementsystem.service.ProjectService;
import dev.kayange.projectmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final ProjectService projectService;
    private final UserService userService;

    @Override
    public Issue getIssueById(Long id) {
        return findIssueById(id);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) {
        projectService.getProjectById(projectId);
        return issueRepository.findIssueByProjectId(projectId);
    }

    @Override
    public void createIssue(IssueRequest request, Long userId) {
        Project project = projectService.getProjectById(request.getProjectId());
        UserEntity user = userService.findUserById(userId);
        if(!project.getTeam().contains(user) || !project.getOwner().getId().equals(user.getId())) throw new ApiException("You are not authorized to add issue");
        var issue = Issue.builder()
                .description(request.getDescription())
                .title(request.getTitle())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .project(project)
                .build();
        issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) {
        Issue issue = findIssueById(issueId);
        Project project = (issue.getProject());
        UserEntity user = userService.findUserById(userId);
        if(!project.getTeam().contains(user)) throw new ApiException("You are not allowed to delete this issue");
        issueRepository.delete(issue);
    }

    @Override
    public void addUserToIssue(Long issueId, Long userId) {
        Issue issue = findIssueById(issueId);
        UserEntity user = userService.findUserById(userId);
        issue.setAssignee(user);
        issueRepository.save(issue);
    }

    @Override
    public void removeUserFromIssue(Long issueId, Long userId) {
        Issue issue = findIssueById(issueId);
        //UserEntity user = userService.findUserById(userId);
        issue.setAssignee(null);
        issueRepository.save(issue);
    }

    @Override
    public void updateIssueStatus(Long issueId, String status, Long userId) {
        Issue issue = findIssueById(issueId);
        UserEntity user = userService.findUserById(userId);
        if(!issue.getAssignee().equals(user) || !issue.getProject().getTeam().contains(user)) throw new ApiException("You are not authorized to update this issue");
        issue.setStatus(status);
        issueRepository.save(issue);
    }

    private Issue findIssueById(Long issueId) {
        return issueRepository.findById(issueId).orElseThrow(()-> new ApiException("Could not find issue with ID "+ issueId));
    }
}
