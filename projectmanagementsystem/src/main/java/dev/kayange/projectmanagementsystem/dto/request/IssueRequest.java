package dev.kayange.projectmanagementsystem.dto.request;

import dev.kayange.projectmanagementsystem.enumaration.Priority;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class IssueRequest {
    @NotEmpty(message = "Issue Title is Required")
    private String title;
    private String description;
    private String status;
    private Long projectId;
    private Priority priority;
    private LocalDate dueDate;
}
