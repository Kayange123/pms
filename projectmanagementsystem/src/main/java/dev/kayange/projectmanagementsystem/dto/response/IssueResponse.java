package dev.kayange.projectmanagementsystem.dto.response;

import dev.kayange.projectmanagementsystem.entity.Project;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class IssueResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long projectId;
    private String priority;
    private String dueDate;
    private Project project;
    private UserEntity assignee;
    private List<String> tags;

}
