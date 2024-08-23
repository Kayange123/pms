package dev.kayange.projectmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.kayange.projectmanagementsystem.enumaration.PlanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private PlanType planType;
    private boolean valid;
    @OneToOne
    @JsonIgnore
    private UserEntity user;
}
