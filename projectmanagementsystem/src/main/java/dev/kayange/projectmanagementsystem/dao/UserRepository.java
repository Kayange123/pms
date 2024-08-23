package dev.kayange.projectmanagementsystem.dao;

import dev.kayange.projectmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT user FROM UserEntity user WHERE user.username = :username")
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUserId(String userId);
}
