package dev.kayange.projectmanagementsystem.dao;

import dev.kayange.projectmanagementsystem.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentByIssueId(Long id);
}
