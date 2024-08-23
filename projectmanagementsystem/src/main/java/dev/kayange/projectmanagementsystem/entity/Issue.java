package dev.kayange.projectmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.kayange.projectmanagementsystem.enumaration.Priority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String status;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDate dueDate;
    @ElementCollection
    private List<String> tags;

    @JsonIgnore
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne
    private UserEntity assignee;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

}
