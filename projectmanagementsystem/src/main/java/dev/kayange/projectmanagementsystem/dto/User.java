package dev.kayange.projectmanagementsystem.dto;

import dev.kayange.projectmanagementsystem.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class User {
    private String firstName;
    private String lastName;
    private String userId;
    private String username;
    private String email;
    private Boolean locked;
    private Boolean enabled;
    List<Role> roles;
    private int projectSize;
}
