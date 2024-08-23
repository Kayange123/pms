package dev.kayange.projectmanagementsystem.dao;

import dev.kayange.projectmanagementsystem.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT token FROM Token token WHERE token.token = :token")
    Optional<Token> findByToken(String token);
}
