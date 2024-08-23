package dev.kayange.projectmanagementsystem.utils;

import dev.kayange.projectmanagementsystem.dto.RegistrationRequest;
import dev.kayange.projectmanagementsystem.dto.User;
import dev.kayange.projectmanagementsystem.dto.response.IssueResponse;
import dev.kayange.projectmanagementsystem.dto.response.ProjectResponse;
import dev.kayange.projectmanagementsystem.entity.Issue;
import dev.kayange.projectmanagementsystem.entity.Project;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import org.springframework.beans.BeanUtils;

public class ObjectMapper {
    public static User convertToUser(UserEntity userEntity) {
        User user = User.builder().build();
        BeanUtils.copyProperties(userEntity, user);
        user.setRoles(userEntity.getRoles());
        user.setUserId(userEntity.getUserId());

        return user;
    }

    public static ProjectResponse convertToProjectResponse(Project project) {
        ProjectResponse response = ProjectResponse.builder().build();
        BeanUtils.copyProperties(project, response);
        return response;
    }

    public static IssueResponse convertToIssueResponse(Issue issue) {
        return IssueResponse.builder()
                .priority(issue.getPriority().name())
                .dueDate(issue.getDueDate().toString())
                .status(issue.getStatus())
                .description(issue.getDescription())
                .title(issue.getTitle())
                .id(issue.getId())
                .project(issue.getProject())
                .assignee(issue.getAssignee())
                .projectId(issue.getProject().getId())
                .tags(issue.getTags())
                .build();
    }

}
