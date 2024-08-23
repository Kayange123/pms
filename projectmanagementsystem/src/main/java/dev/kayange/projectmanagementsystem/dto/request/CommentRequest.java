package dev.kayange.projectmanagementsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class CommentRequest {
    private Long issueId;
    private String comment;
}
