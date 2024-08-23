package dev.kayange.projectmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity @Table(name="users")
public class UserEntity implements UserDetails, Principal {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String userId;
    private String lastName;
    private String username;

    @Column(unique = true, updatable = false, nullable = false)
    private String email;
    private String password;

    private Boolean locked = false;
    private Boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Issue> assignedIssues = new ArrayList<>();

    private int projectSize;

    @Override
    public String getName() {
        return getFullName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(
            role-> new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
