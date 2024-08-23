package dev.kayange.projectmanagementsystem.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProjectRequest {
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private List<String> tags;
}
