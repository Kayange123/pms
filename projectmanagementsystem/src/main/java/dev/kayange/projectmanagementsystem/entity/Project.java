package dev.kayange.projectmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="projects")
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String publicId;
    private String description;
    private String imageUrl;
    private String category;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
    private Chat chat;

    @ManyToOne
    @JsonIgnore
    private UserEntity owner;

    @JsonIgnore
    @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Issue> issues = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    private List<UserEntity> team = new ArrayList<>();
}
