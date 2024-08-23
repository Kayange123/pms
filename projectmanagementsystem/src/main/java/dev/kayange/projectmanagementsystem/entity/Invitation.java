package dev.kayange.projectmanagementsystem.entity;

import dev.kayange.projectmanagementsystem.enumaration.InvitationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="invitations")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private String email;
    @Enumerated(EnumType.STRING)
    private InvitationStatus status;
    private Long projectId;
}
