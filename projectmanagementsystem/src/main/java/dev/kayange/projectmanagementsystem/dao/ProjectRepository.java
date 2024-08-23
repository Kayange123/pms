package dev.kayange.projectmanagementsystem.dao;

import dev.kayange.projectmanagementsystem.entity.Project;
import dev.kayange.projectmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findProjectByOwner(UserEntity user);
    List<Project> findProjectByNameContainingAndTeamContains(String name, UserEntity user);
    @Query("SELECT p FROM Project p JOIN p.team t WHERE t = :user")
    List<Project> findProjectByTeam(@Param("user") UserEntity user);
    List<Project> findProjectByTeamContainingOrOwner(UserEntity user, UserEntity owner);
}
