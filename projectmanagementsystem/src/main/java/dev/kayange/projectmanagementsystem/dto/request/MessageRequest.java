package dev.kayange.projectmanagementsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @Data
@NoArgsConstructor @Builder
public class MessageRequest {
    private Long projectId;
    private String message;
}
